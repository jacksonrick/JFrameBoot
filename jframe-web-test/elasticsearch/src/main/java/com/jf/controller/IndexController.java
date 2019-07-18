package com.jf.controller;

import com.jf.entity.Article;
import com.jf.entity.Pagination;
import com.jf.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-21
 * Time: 14:00
 */
@Controller
public class IndexController {

    @Resource
    private ArticleService articleService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }


    /**
     * 添加数据，初始化执行
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public String add() {
        List<Article> poems = new ArrayList<>();
        poems.add(new Article(1, "距离马云退休仅剩2个月！阿里公布一张照片，让网友赞叹不已！", "众所周知，在中国只要我们一提起马云的名字，可以说是无人不知道无人不晓得，就算是在偏远的山区，很多人对马云都还是非常熟悉的，因为马云不仅创立了伟大的阿里巴巴，让淘宝和天猫走入了千家万户，而且马云还十分的热爱公益活动，经常会举办和参加一些公益助农等活动，因此马云也成为了很多人羡慕的对象，甚至还有不少人将马云当做自己的偶像！"));
        poems.add(new Article(2, "关于近日K12在线教育热的思考", "K12的在线双师大班成了最热的赛道，这是行业发展路径的必然。热到什么程度？不是谁家有了多少收入谁家投了多少广告，而是和教育行业基本八竿子打不着的大佬们都因为36kr那篇《2019「K12网校」大爆发，决战在线教育千亿美元市值 | Pro深度》而对在线教育产生了浓厚的兴趣。"));
        poems.add(new Article(3, "支付宝/财付通霸占移动支付市场：谁是真正老大？", "支付宝和腾讯财付通作为国内两大移动支付龙头服务商，双方市场份额仍有差距，支付宝呈现领先态势。占据了53.8%的份额高居第一，腾讯财付通(含微信支付)位列第二占39.9%，落后支付宝13.9百分点。"));
        poems.add(new Article(4, "互联网信息服务投诉平台正式上线运行", "据了解，互联网信息服务投诉平台是在工业和信息化部指导下，由中国互联网协会建设运营的第三方投诉渠道，投诉平台坚持“以人民为中心”的发展思想，定位于“绿色通道”，旨在快速化解用户与企业之间的服务纠纷，是保护用户合法权益的重要途径，也是行业自律和社会监督的重要组成部分、政府监管的有力支撑。"));
        poems.add(new Article(5, "2019互联网全球30强：亚马逊居首BAT位列中国前三", "2018年互联网行业迎来2014年以来的新一轮上市浪潮。去年，美国、香港和内地资本市场纷纷向互联网企业为代表的新经济公司抛出橄榄枝，一年内我国新上市的互联网企业达25家，经营范围涵盖生活服务、文体娱乐、互联网金融、音视频、电子商务等众多领域，包括美团点评、腾讯音乐、拼多多、爱奇艺等多家垂直领域龙头企业。"));
        poems.add(new Article(6, "卖了辣么多外卖的美团，巨亏近2千亿！你信吗？", "乔布斯创造了美团，你信吗？但美团却不能单凭乔布斯走出亏损，你觉得呢？"));
        poems.add(new Article(7, "“互联网+电影”正在改变人们的生活", "今年上半年，在“2019爱奇艺世界大会”上，爱奇艺创始人、CEO龚宇宣布，爱奇艺将推出原创电影计划。互联网入局电影，不是偶然而是必然。近年来，互联网与电影的融合越来越显著、越来越密切。电影本就是技术与艺术交融的产物，互联网作为一种基础的技术工具，正在改写电影产业的未来。"));
        poems.add(new Article(8, "Facebook想推出加密货币？特朗普的“三连击”感受一下", "全球最大社交平台Facebook拟推出加密数字货币Libra。对此，特朗普在推特上唱衰，称在美国只有一种真正的货币，它的名字是美元。"));
        poems.add(new Article(9, "拆解拼多多、趣头条、小红书背后的上海互联网基因", "互联网的创业江湖，时常会被划分为不同的门派。以秦岭淮河为界，一南一北，中国的互联网也形成了所谓的“北派”和“海派”。在传统的刻板印象里，北派务虚，讲排场，好大喜功；以上海为代表的“海派”务实，讲套路，精于算计。"));
        poems.add(new Article(10, "北京百度注册资本增加70亿元涨幅超109%，向海龙卸任监事", "蓝鲸TMT频道7月12日讯，近日，北京百度网讯科技有限公司发生多项工商变更，注册资本由64.2128亿元人民币增加至134.2128亿元人民币，增加70亿元人民币，涨幅超109%。"));
        poems.add(new Article(11, "抢5G先机？美半导体巨头走出这一步", "7月4日报道外媒称，全球最大的半导体制造设备供应商之一美国应用材料公司近日同意斥资22亿美元（1美元约合6.9元人民币），收购原日立制作所旗下的设备制造商国际电气公司（东京）所有已发行股份。达成协议的日期为7月1日。预计收购手续将在今后一年内完成。"));
        poems.add(new Article(12, "数据云上跑 市民省心又省事", "到“云”上去！一场大规模的“迁徙”正在广东紧锣密鼓地进行。近日，广东潮州举行“数字政府”战略合作框架协议签订仪式，并对外透露，正全力推动地市非涉密政务信息系统迁移上云。而早在年初，广东省民政厅召开政务信息系统接管及迁移上云工作启动会，提出将在年中将所有需迁云系统迁移上云。"));

        poems.forEach(article -> {
            articleService.save(article);
        });
        return "SUCCESS";
    }

    /**
     * 普通搜索
     *
     * @param pageIndex
     * @param pageSize
     * @param title
     * @param content
     * @return
     */
    @RequestMapping("/search")
    @ResponseBody
    public Page<Article> search(@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                String title, String content) {
        Pageable pageable = new PageRequest(pageIndex, pageSize);
        Page<Article> articles = articleService.search(title, content, pageable);
        return articles;
    }

    /**
     * 分词、拼音、高亮搜索
     *
     * @param pageIndex
     * @param pageSize
     * @param keyword
     * @return
     */
    @RequestMapping("/searchAll")
    @ResponseBody
    public Pagination<Article> searchAll(@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                         String keyword) {
        Pageable pageable = new PageRequest(pageIndex, pageSize);
        Page<Article> articles = articleService.searchPinyin(keyword, pageable);

        return new Pagination<Article>(articles.getContent(), articles.getPageable().getOffset(), articles.getTotalPages());
    }

}
