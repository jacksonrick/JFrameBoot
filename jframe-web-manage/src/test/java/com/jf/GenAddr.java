package com.jf;

import com.jf.database.model.Address;
import com.jf.service.system.AddrService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 生成地址库js文件（需要修改输出的路径）
 * User: xujunfei
 * Date: 2018-01-30
 * Time: 11:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GenAddr {

    @Resource
    private AddrService addrService;

    ///city-picker插件 JSON地址库///
    @Test
    public void test() {
        String start = "(function(factory){if(typeof define==='function'&&define.amd){define('ChineseDistricts',[],factory)}else{factory()}})(function(){var ChineseDistricts={";
        start += "86:{'A-G':[{code:'340000',address:'安徽省'},{code:'110000',address:'北京'},{code:'500000',address:'重庆'},{code:'350000',address:'福建省'},{code:'620000',address:'甘肃省'},{code:'440000',address:'广东省'},{code:'450000',address:'广西壮族自治区'},{code:'520000',address:'贵州省'}],'H-K':[{code:'460000',address:'海南省'},{code:'130000',address:'河北省'},{code:'230000',address:'黑龙江省'},{code:'410000',address:'河南省'},{code:'420000',address:'湖北省'},{code:'430000',address:'湖南省'},{code:'320000',address:'江苏省'},{code:'360000',address:'江西省'},{code:'220000',address:'吉林省'}],'L-S':[{code:'210000',address:'辽宁省'},{code:'150000',address:'内蒙古自治区'},{code:'640000',address:'宁夏回族自治区'},{code:'630000',address:'青海省'},{code:'370000',address:'山东省'},{code:'310000',address:'上海'},{code:'140000',address:'山西省'},{code:'610000',address:'陕西省'},{code:'510000',address:'四川省'}],'T-Z':[{code:'120000',address:'天津'},{code:'650000',address:'新疆维吾尔自治区'},{code:'540000',address:'西藏自治区'},{code:'530000',address:'云南省'},{code:'330000',address:'浙江省'}]},";

        Address a1 = new Address();
        a1.setLevel(1);
        List<Address> list1 = addrService.findAddrList(a1);
        System.out.println(list1.size());

        for (Address addr1 : list1) {
            String prov = addr1.getId() + ":{";

            Address a2 = new Address();
            a2.setParent(addr1.getId());
            List<Address> list2 = addrService.findAddrList(a2);
            for (Address addr2 : list2) {
                prov += addr2.getId() + ":'" + addr2.getName() + "',";
            }
            prov += "},";
            start += prov;
        }

        // dist
        Address a2 = new Address();
        a2.setLevel(2);
        List<Address> list2 = addrService.findAddrList(a2);
        for (Address addr2 : list2) {
            String city = addr2.getId() + ":{";

            Address a3 = new Address();
            a3.setParent(addr2.getId());
            List<Address> list3 = addrService.findAddrList(a3);
            for (Address addr3 : list3) {
                city += addr3.getId() + ":'" + addr3.getName() + "',";
            }
            city += "},";
            start += city;
        }

        // street
        Address a3 = new Address();
        a3.setLevel(3);
        List<Address> list3 = addrService.findAddrList(a3);
        for (Address addr3 : list3) {
            String city = addr3.getId() + ":{";

            Address a4 = new Address();
            a4.setParent(addr3.getId());
            List<Address> list4 = addrService.findAddrList(a4);
            for (Address addr4 : list4) {
                city += addr4.getId() + ":'" + addr4.getName() + "',";
            }
            city += "},";
            start += city;
        }

        String end = start + "};if(typeof window!=='undefined'){window.ChineseDistricts=ChineseDistricts}return ChineseDistricts});";

        try {
            Writer wr = new FileWriter("/Users/xujunfei/Downloads/city-picker.data.js");
            wr.write(end);
            wr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
