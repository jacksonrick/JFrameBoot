package com.jf.service.system;

import com.jf.mapper.AddressMapper;
import com.jf.model.Address;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xujunfei on 2017/5/9.
 */
@Service
public class AddrService {

    @Resource
    private AddressMapper addressMapper;

    /**
     * @param condition
     * @return
     */
    public List<Address> findAddrList(Address condition) {
        return addressMapper.findByCondition(condition);
    }

    /**
     * @param address
     * @return
     */
    public int update(Address address) {
        return addressMapper.update(address);
    }

    /**
     * @param address
     * @return
     */
    public int insert(Address address) {
        return addressMapper.insert(address);
    }

    /**
     * @param id
     * @return
     */
    public int delete(Integer id) {
        return addressMapper.delete(id);
    }

}
