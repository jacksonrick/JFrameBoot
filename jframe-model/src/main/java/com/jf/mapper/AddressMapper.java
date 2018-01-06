package com.jf.mapper;

import com.jf.model.custom.BaseVo;
import com.jf.model.Address;

import java.util.List;

/**
 * AddressMapper Interface
 * @date 2017年05月09日 上午 11:06:55
 * @author jfxu
 */
public interface AddressMapper {

	List<Address> findByCondition(BaseVo baseVo);

	int insert(Address bean);

	int update(Address bean);

	int delete(Integer id);

}