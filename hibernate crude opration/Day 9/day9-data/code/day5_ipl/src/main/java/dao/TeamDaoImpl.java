package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pojos.Team;
import static utils.DBUtils.*;

public class TeamDaoImpl implements TeamDao {
	private Connection cn;
	private PreparedStatement pst1, pst2;

	public TeamDaoImpl() throws SQLException {
		cn=getConnection();
		pst1 = cn.prepareStatement("select abbrevation from teams");
		pst2=cn.prepareStatement("select * from teams where abbrevation=?");
		System.out.println("team dao created");
	}

	@Override
	public List<String> getTeamsAbbreviations() throws SQLException {
		List<String> abbreviations = new ArrayList<>();
		// exec query n process RST
		try (ResultSet rst = pst1.executeQuery()) {
			while (rst.next())
				abbreviations.add(rst.getString(1));
		}
		return abbreviations;
	}

	@Override
	public Team getTeamDetails(String abbreviation) throws SQLException {
		// set IN param
		pst2.setString(1, abbreviation);
		try (ResultSet rst = pst2.executeQuery()) {
			if (rst.next())
				return new Team(rst.getInt(1), rst.getString(2), abbreviation, rst.getString(3), rst.getInt(4),
						rst.getDouble(5), rst.getInt(6));
		}
		return null;
	}

	public void cleanUp() throws SQLException {
		if (pst1 != null)
			pst1.close();
		if (pst2 != null)
			pst2.close();
		System.out.println("team dao cleaned up !");
	}

}
