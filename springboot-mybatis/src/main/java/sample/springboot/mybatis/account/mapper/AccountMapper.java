package sample.springboot.mybatis.account.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import sample.springboot.mybatis.account.entity.Account;

@Mapper
public interface AccountMapper {

	@Select(value = "select * from account where id = #{id}")
	Account get(@Param(value = "id") Integer id);

	@Insert(value = "insert into account(id, name, money) values(#{id}, #{name}, #{money})")
	int insert(Account account);

	int batchInsert(@Param(value = "accounts") List<Account> accounts);

	@Update(value = "update account set name = #{name}, money = #{money} where id = #{id}")
	int update(Account account);

	@Delete(value = "delete from account where id = #{id}")
	int delete(@Param(value = "id") Integer id);

	int clean();

	List<Account> findList(Account account);

}
