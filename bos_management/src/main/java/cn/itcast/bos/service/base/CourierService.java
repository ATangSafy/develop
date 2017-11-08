package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Courier;

/**
 * 快递员操作接口
 * @author min
 *
 */
public interface CourierService {

	public void save(Courier courier);

	public Page<Courier> findPageData(Pageable pageable);

	public void delBatch(String[] idArray);
 
}
