package sample.springboot.mybatis.account.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import sample.springboot.mybatis.account.entity.Account;
import sample.springboot.mybatis.entity.Page;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

	private static final Integer INIT_SIZE = 15; // 设置为奇数
	private static final Integer HALF_SIZE = INIT_SIZE / 2 + 1; // 大于一半

	@Autowired
	private AccountService accountService;

	static {
		Assert.assertTrue("非有效初始化数据", INIT_SIZE > 0);
		Assert.assertTrue("非有效初始化数据", INIT_SIZE < 100);
		Assert.assertNotSame("非有效初始化数据", INIT_SIZE, ((int) (INIT_SIZE / 2)) * 2);
	}

	// init
	@Before
	public void before() {
		List<Account> accounts = new ArrayList<Account>();
		for (int i = 1; i <= INIT_SIZE; i++) {
			Account account = new Account();
			account.setId(i);
			account.setName(this.newName());
			account.setMoney(this.newMoney());
			accounts.add(account);
		}
		accountService.batchInsert(accounts);
		// end
	}

	// clear
	@After
	public void after() {
		accountService.clean();
	}

	@Test
	public void testGet() {
		Account account = accountService.get(1);
		Assert.assertNotNull("数据未找到", account);
		account = accountService.get(Integer.MIN_VALUE);
		Assert.assertNull("数据已存在", account);
	}

	@Test
	public void testInsert() {
		int id = INIT_SIZE + 1;

		Account account = new Account();
		account.setId(id);
		account.setName(this.newName());
		account.setMoney(this.newMoney());
		accountService.insert(account);

		Account queryAccount = accountService.get(id);
		Assert.assertNotNull("数据未找到", queryAccount);
		Assert.assertEquals("名字不匹配", account.getName(), queryAccount.getName());

		// delete
		accountService.delete(id);
	}

	@Test
	public void testUpdate() {
		Account account = new Account();
		account.setName(this.newName());
		account.setMoney(this.newMoney());
		accountService.update(account, 1);

		// find from db
		Account queryAccount = accountService.get(1);
		Assert.assertNotNull("数据未找到", account);
		Assert.assertEquals("名字未修改", account.getName(), queryAccount.getName());
		Assert.assertEquals("金额未修改", account.getMoney(), queryAccount.getMoney(), 0.01);
	}

	@Test(expected = RuntimeException.class)
	public void testUpdateNotExists() {
		Account account = new Account();
		account.setName(this.newName());
		account.setMoney(this.newMoney());
		accountService.update(account, Integer.MIN_VALUE);
	}

	@Test
	public void testDelete() {
		int id = INIT_SIZE + 2;
		// insert 
		Account account = new Account();
		account.setId(id);
		account.setName(this.newName());
		account.setMoney(this.newMoney());
		accountService.insert(account);

		Account queryAccount = accountService.get(id);
		Assert.assertNotNull("数据未找到", queryAccount);

		accountService.delete(id);

		// find max id data
		queryAccount = accountService.get(id);
		Assert.assertNull("数据未刪除", queryAccount);
	}

	@Test
	public void testDeleteNotExists() {
		accountService.delete(Integer.MIN_VALUE);
	}

	@Test(expected = RuntimeException.class)
	public void testDeleteNotExistsAndCheck() {
		accountService.delete(Integer.MIN_VALUE, true);
	}

	@Test
	public void testFindList() {
		Account account = new Account();
		account.setName("name:%");
		List<Account> datas = accountService.findList(account);
		Assert.assertEquals("查询失败", INIT_SIZE.intValue(), datas.size());
	}

	@Test
	public void testFindPageAccountIntegerInteger() {
		// 查询一部分
		Page<Account> page = accountService.findPage(new Account(), 1, HALF_SIZE);
		Assert.assertEquals("查询分页数据数量不匹配", INIT_SIZE.intValue(), page.getCount().intValue());
		Assert.assertEquals("查询分页数据数量不匹配", HALF_SIZE.intValue(), page.getDataList().size());

		// 查询剩余
		page = accountService.findPage(new Account(), 2, HALF_SIZE);
		Assert.assertEquals("查询分页数据数量不匹配", INIT_SIZE.intValue(), page.getCount().intValue());
		Assert.assertEquals("查询分页数据数量不匹配", (INIT_SIZE - HALF_SIZE), page.getDataList().size());

		// 查询所有
		page = accountService.findPage(new Account(), 1, INIT_SIZE + 1);
		Assert.assertEquals("查询分页数据数量不匹配", INIT_SIZE.intValue(), page.getCount().intValue());
		Assert.assertEquals("查询分页数据数量不匹配", INIT_SIZE.intValue(), page.getDataList().size());
	}

	@Test
	public void testFindPageAccountIntegerIntegerString() {
		Page<Account> page = accountService.findPage(new Account(), 1, HALF_SIZE, "id desc");
		Assert.assertEquals("查询分页数据数量不匹配", INIT_SIZE.intValue(), page.getCount().intValue());
		Assert.assertEquals("查询分页数据数量不匹配", HALF_SIZE.intValue(), page.getDataList().size());
		Assert.assertEquals("非最大id", INIT_SIZE.intValue(), page.getDataList().get(0).getId().intValue());
	}

	private String newName() {
		return "name:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	private Double newMoney() {
		return new BigDecimal((100) * Math.random() + 20).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

}
