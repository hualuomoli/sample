package sample.springboot.mybatis.account.service;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sample.springboot.mybatis.account.entity.Account;
import sample.springboot.mybatis.account.mapper.AccountMapper;
import sample.springboot.mybatis.entity.Page;
import sample.springboot.mybatis.plugin.mybatis.interceptor.pagination.PaginationInterceptor;

@Service
@Transactional(readOnly = true)
public class AccountService {

	@Autowired
	private AccountMapper accountMapper;

	public Account get(Integer id) {
		return accountMapper.get(id);
	}

	@Transactional(readOnly = false)
	public void insert(Account account) {
		int updated = accountMapper.insert(account);
		Validate.isTrue(updated == 1, "添加失败");
	}

	@Transactional(readOnly = false)
	public void batchInsert(List<Account> accounts) {
		accountMapper.batchInsert(accounts);
	}

	@Transactional(readOnly = false)
	public void update(Account account, Integer id) {
		account.setId(id);
		int updated = accountMapper.update(account);
		Validate.isTrue(updated == 1, "修改失败");
	}

	@Transactional(readOnly = false)
	public void delete(Integer id) {
		this.delete(id, false);
	}

	@Transactional(readOnly = false)
	public void delete(Integer id, boolean check) {
		int updated = accountMapper.delete(id);
		if (!check) {
			return;
		}
		Validate.isTrue(updated == 1, "刪除失败");
	}

	@Transactional(readOnly = false)
	public void clean() {
		accountMapper.clean();
	}

	public List<Account> findList(Account account) {
		return accountMapper.findList(account);
	}

	public List<Account> findList(Account account, String orderBy) {
		// 设置排序
		PaginationInterceptor.setListOrder(orderBy);
		return accountMapper.findList(account);
	}

	public Page<Account> findPage(Account account, Integer pageNumber, Integer pageSize) {
		// 设置分页信息
		PaginationInterceptor.setPagination(pageNumber, pageSize);
		// 查询数据
		List<Account> datas = accountMapper.findList(account);
		// 获取数据总量
		Integer count = PaginationInterceptor.getCount();
		// 组装返回数据
		return new Page<Account>(pageNumber, pageSize, count, datas);
	}

	public Page<Account> findPage(Account account, Integer pageNumber, Integer pageSize, String orderBy) {
		// 设置分页信息(含排序)
		PaginationInterceptor.setPagination(pageNumber, pageSize, orderBy);
		// 查询数据
		List<Account> datas = accountMapper.findList(account);
		// 获取数据总量
		Integer count = PaginationInterceptor.getCount();
		// 组装返回数据
		return new Page<Account>(pageNumber, pageSize, count, datas);
	}

}
