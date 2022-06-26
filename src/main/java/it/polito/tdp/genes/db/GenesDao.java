package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	

				
	public List<String> getVertici () {
		String sql="SELECT distinct c.Localization AS localization "
				+ "FROM classification c";
		List<String> result= new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				String s= res.getString("localization");
				result.add(s);
				
			}
			
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
		
	}

	public int getPeso (String loc1, String loc2) {
		String sql="SELECT COUNT(DISTINCT i.`Type`) AS peso "
				+ "FROM interactions i, classification c1, classification c2 "
				+ "WHERE c1.GeneID=i.GeneID1 AND c2.GeneID=i.GeneID2  AND (c2.Localization=? OR c1.Localization=?) AND (c1.Localization=? OR c2.Localization=?)";
		int peso=0;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, loc1);
			st.setString(2, loc1);
			st.setString(3, loc2);
			st.setString(4,  loc2);
			ResultSet res = st.executeQuery();
			if (res.next()) {
				peso=res.getInt("peso");
			}
			conn.close();
			return peso;
			
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
				
	}
	
	
	
}
