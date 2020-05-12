package com.jf.service;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-07-30
 * Time: 14:11
 */
@Service
public class ActivitiService {

    @Resource
    private IdentityService identityService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;
    @Resource
    private ProcessEngine processEngine;
    @Autowired
    private RepositoryService repositoryService;

    /**
     * 添加用户
     */
    public void addUser() {
        User user1 = identityService.newUser("1");
        user1.setLastName("老王");
        identityService.saveUser(user1);

        User user2 = identityService.newUser("2");
        user2.setLastName("小李");
        identityService.saveUser(user2);

        User user3 = identityService.newUser("3");
        user3.setLastName("老总");
        identityService.saveUser(user3);
    }

    /**
     * 添加组并绑定
     */
    public void addGroup() {
        Group group1 = identityService.newGroup("1");
        group1.setName("部门主管");
        identityService.saveGroup(group1);

        Group group2 = identityService.newGroup("2");
        group2.setName("行政主管");
        identityService.saveGroup(group2);

        Group group3 = identityService.newGroup("3");
        group3.setName("总经理");
        identityService.saveGroup(group3);

        identityService.createMembership("1", "1");
        identityService.createMembership("2", "2");
        identityService.createMembership("3", "3");
    }

    /**
     * 申请
     *
     * @param userId
     * @param day
     */
    public String apply(String userId, Integer day) {
        // //启动流程实例，"test"是BPMN模型文件里process元素的id
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("test");
        //流程实例启动后，流程会跳转到请假申请节点
        Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
        //设置请假申请任务的执行人
        taskService.setAssignee(task.getId(), userId);

        //设置流程参数：请假天数和表单ID
        Map<String, Object> args = new HashMap<>();
        args.put("days", day);
        args.put("formId", "formId");
        taskService.complete(task.getId(), args);
        return instance.getId();
    }

    /**
     * 查询审核列表
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     */
    public void applyList(String userId, Integer pageNum, Integer pageSize) {
        //查出当前登录用户所在的用户组
        List<Group> groups = identityService.createGroupQuery().groupMember(userId).list();
        List<String> groupIds = groups
                .stream()
                .map(group -> group.getId())
                .collect(Collectors.toList());

        //查询用户组的待审批请假流程列表
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey("test")
                .taskCandidateGroupIn(groupIds).listPage(pageNum - 1, pageSize);

        //根据流程实例ID查询请假申请表单数据
        List<String> instanceIds = tasks.stream()
                .map(task -> task.getProcessInstanceId())
                .collect(Collectors.toList());

        for (String instance : instanceIds) {
            System.out.println("### " + instance);
        }
    }

    /**
     * 审批
     *
     * @param userId
     * @param auditId
     * @param taskId
     * @param result
     */
    public void audit(String userId, String auditId, String taskId, Boolean result) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if (result) {//审批通过
            //设置流程参数：审批ID
            Map<String, Object> args = new HashMap<>();
            args.put("auditId", auditId);

            //设置审批任务的执行人
            taskService.claim(task.getId(), userId);
            //完成审批任务
            taskService.complete(task.getId(), args);
        } else {
            //审批不通过，结束流程
            runtimeService.deleteProcessInstance(task.getProcessInstanceId(), "");
        }
    }

    /**
     * 生成图表
     *
     * @param processInstanceId
     * @return
     */
    public InputStream getDiagram(String processInstanceId) {
        //获得流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = StringUtils.EMPTY;
        if (processInstance == null) {
            //查询已经结束的流程实例
            HistoricProcessInstance processInstanceHistory =
                    historyService.createHistoricProcessInstanceQuery()
                            .processInstanceId(processInstanceId).singleResult();
            if (processInstanceHistory == null)
                return null;
            else
                processDefinitionId = processInstanceHistory.getProcessDefinitionId();
        } else {
            processDefinitionId = processInstance.getProcessDefinitionId();
        }

        //获取BPMN模型对象
        BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
        //获取流程实例当前的节点，需要高亮显示
        List<String> currentActs = Collections.EMPTY_LIST;
        if (processInstance != null)
            currentActs = runtimeService.getActiveActivityIds(processInstance.getId());

        return processEngine.getProcessEngineConfiguration()
                .getProcessDiagramGenerator()
                .generateDiagram(model, "png", currentActs);
    }

}
