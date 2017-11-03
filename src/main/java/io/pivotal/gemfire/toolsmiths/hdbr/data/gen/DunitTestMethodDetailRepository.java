package io.pivotal.gemfire.toolsmiths.hdbr.data.gen;

import com.nurkiewicz.jdbcrepository.JdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.db.DunitTestMethodDetailDB;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.DunitTestMethodDetail;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import com.nurkiewicz.jdbcrepository.RowUnmapper;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
@Repository
public class DunitTestMethodDetailRepository extends JdbcRepository<DunitTestMethodDetail, Integer>
{

	final static Logger logger = LoggerFactory.getLogger (DunitTestMethodDetailRepository.class);

	@Autowired
	private JdbcOperations jdbcOperations;

	protected JdbcOperations getJdbcOperations()
	{
		return this.jdbcOperations;
	}

	public DunitTestMethodDetailRepository()
	{
		super (DunitTestMethodDetailDB.ROW_MAPPER, DunitTestMethodDetailDB.ROW_UNMAPPER, DunitTestMethodDetailDB.getTableName ());
	}

	public DunitTestMethodDetailRepository(RowMapper<DunitTestMethodDetail> rowMapper, RowUnmapper<DunitTestMethodDetail> rowUnmapper, String idColumn)
	{
		super (DunitTestMethodDetailDB.ROW_MAPPER, DunitTestMethodDetailDB.ROW_UNMAPPER, DunitTestMethodDetailDB.getTableName (), idColumn);
	}

	@Override
	protected DunitTestMethodDetail postCreate(DunitTestMethodDetail entity, Number generatedId)
	{
		entity.setId(generatedId.intValue());
		entity.setPersisted(true);
		return entity;
	}


	public List<DunitTestMethodDetail> getDunitTestMethodDetailsByRunId (Long runId)
	{
		String sql = "select * from " + DunitTestMethodDetailDB.getTableName() + " where " + DunitTestMethodDetailDB.COLUMNS.RUN_ID.getColumnName() + " = ? ";
		return this.jdbcOperations.query (sql, new Object[] { runId }, DunitTestMethodDetailDB.ROW_MAPPER);
	}


	/* START Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

	/* END Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

}

