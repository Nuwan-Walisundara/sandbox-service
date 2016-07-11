package com.wso2telco.dep.tpservice.dao;

import java.sql.SQLException;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wso2telco.dep.tpservice.model.TokenDTO;
import com.wso2telco.dep.tpservice.util.Constants;
import com.wso2telco.dep.tpservice.util.Constants.Tables;
import com.wso2telco.dep.tpservice.util.Event;
import com.wso2telco.dep.tpservice.util.Status;
import com.wso2telco.dep.tpservice.util.exception.BusinessException;
import com.wso2telco.dep.tpservice.util.exception.GenaralError;

public class EventHistoryDAO {

	private static Logger log = LoggerFactory.getLogger(EventHistoryDAO.class);

	// event related DAO layer for token invalidate
	public void invalidateToken(TokenDTO tokenDto) throws SQLException {

		DBI dbi = JDBIUtil.getInstance();
		Handle h = dbi.open();

		try {
			// for single transaction with TokenDAO
			h.getConnection().setAutoCommit(false);
			h.begin();

			StringBuilder sql_event = new StringBuilder();

			sql_event.append(" INSERT ");
			sql_event.append(" INTO ").append(Tables.TABLE_TSTEVENT.toString());
			sql_event.append(" ( jobname , text, event , status ) ");
			sql_event.append("VALUES ");
			sql_event.append("(? , ? , ? , ?)");

			h.execute(sql_event.toString(), Constants.CONTEXT_TOKEN, "TokenID "+tokenDto.getId(), Status.INVALIDATE_SUCCESS, Event.INVALIDATE_TOKEN);

			// for invalidating Token through TokenDAO layer
			TokenDAO tokenObj = new TokenDAO();
			tokenObj.invalidatingToken( tokenDto);

			// When both TokenDAO & EventDAO executed without error
			h.commit();
			log.debug("Token Invalidation Success for the token id "+ tokenDto.getId());

		} catch (Exception e) {

			h.rollback();
			log.error("EventHistoryDAO", "invalidateToken()", e);
			throw new SQLException("Could not invalidate token");

		} finally {
			h.close();
		}

	}
	
	//Event insertion for the status of regenerated token
	public void regenerateTokenEvent(TokenDTO newTokenDTO) throws SQLException {
		
		DBI dbi_reg = JDBIUtil.getInstance();
		Handle h_reg = dbi_reg.open();

		try {

			StringBuilder sql_event = new StringBuilder();
			sql_event.append(" INSERT ");
			sql_event.append(" INTO ").append(Tables.TABLE_TSTEVENT.toString());
			sql_event.append(" ( jobname , text, event , status ) ");
			sql_event.append("VALUES ");
			sql_event.append("(? , ? , ? , ?)");

			if (newTokenDTO == null) {

				h_reg.execute(sql_event.toString(), Constants.CONTEXT_TOKEN, "TokenID " + newTokenDTO.getId(), Status.REGENARATE_FAIL, Event.RE_GENARATE_TOKEN);

			} else {

				h_reg.execute(sql_event.toString(), Constants.CONTEXT_TOKEN, "TokenID " + newTokenDTO.getId(), Status.REGENARATE_SUCSESS, Event.RE_GENARATE_TOKEN);

			}

		} catch (Exception e) {
			log.error("EventHistoryDAO", "regenerateTokenEvent()", e);
			throw new SQLException("Could not insert regenerated token event");
		} finally {
			h_reg.close();
		}
	}
	
	
	//Event insertion for saved newly regenerated token
	public void saveTokenEvent(TokenDTO tokenDto) throws SQLException {

		DBI dbi_save = JDBIUtil.getInstance();
		Handle h_save = dbi_save.open();

		try {
			StringBuilder sql_event = new StringBuilder();

			sql_event.append(" INSERT ");
			sql_event.append(" INTO ").append(Tables.TABLE_TSTEVENT.toString());
			sql_event.append(" ( jobname , text, event , status ) ");
			sql_event.append("VALUES ");
			sql_event.append("(? , ? , ? , ?)");

			h_save.execute(sql_event.toString(), Constants.CONTEXT_TOKEN, "TokenID "+tokenDto.getId(), Status.REGENERATE_TOKEN_SAVE, Event.SAVED_TOKEN);	
			
			log.debug("Event creation for the newly Regenerated Token ");
		
		} catch (Exception e) {

			log.error("EventHistoryDAO", "saveTokenEvent()", e);
			throw new SQLException("Could not insert event for saved token");

		} finally {
			h_save.close();
		}

	}
	
}

	
	
	


