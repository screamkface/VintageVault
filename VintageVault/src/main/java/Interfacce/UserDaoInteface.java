package Interfacce;

import java.sql.SQLException;

import Beans.UserBean;

public interface UserDaoInteface {
	public void doSave(UserBean ub) throws SQLException;

	public boolean checkUserEmailExistance(String email) throws SQLException;

	public UserBean doRetrieveByEmailAndPass(String email, String password) throws SQLException;

	public boolean checkUserIsAdmin(int id) throws SQLException;
}
