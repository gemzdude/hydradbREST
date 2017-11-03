package io.pivotal.gemfire.toolsmiths.hdbr.data.gen;

import com.nurkiewicz.jdbcrepository.JdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.db.HydraTestDetailDB;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraTestDetail;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import com.nurkiewicz.jdbcrepository.RowUnmapper;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
@Repository
public class HydraTestDetailRepository extends JdbcRepository<HydraTestDetail, Integer>
{

	final static Logger logger = LoggerFactory.getLogger (HydraTestDetailRepository.class);

	@Autowired
	private JdbcOperations jdbcOperations;

	protected JdbcOperations getJdbcOperations()
	{
		return this.jdbcOperations;
	}

	public HydraTestDetailRepository()
	{
		super (HydraTestDetailDB.ROW_MAPPER, HydraTestDetailDB.ROW_UNMAPPER, HydraTestDetailDB.getTableName ());
	}

	public HydraTestDetailRepository(RowMapper<HydraTestDetail> rowMapper, RowUnmapper<HydraTestDetail> rowUnmapper, String idColumn)
	{
		super (HydraTestDetailDB.ROW_MAPPER, HydraTestDetailDB.ROW_UNMAPPER, HydraTestDetailDB.getTableName (), idColumn);
	}

	@Override
	protected HydraTestDetail postCreate(HydraTestDetail entity, Number generatedId)
	{
		entity.setId(generatedId.intValue());
		entity.setPersisted(true);
		return entity;
	}


	public List<HydraTestDetail> getHydraTestDetailsByHydraRunId (Long hydraRunId)
	{
		String sql = "select * from " + HydraTestDetailDB.getTableName() + " where " + HydraTestDetailDB.COLUMNS.HYDRA_RUN_ID.getColumnName() + " = ? ";
		return this.jdbcOperations.query (sql, new Object[] { hydraRunId }, HydraTestDetailDB.ROW_MAPPER);
	}

	public List<HydraTestDetail> getHydraTestDetailsByHydraTestId (Long hydraTestId)
	{
		String sql = "select * from " + HydraTestDetailDB.getTableName() + " where " + HydraTestDetailDB.COLUMNS.HYDRA_TEST_ID.getColumnName() + " = ? ";
		return this.jdbcOperations.query (sql, new Object[] { hydraTestId }, HydraTestDetailDB.ROW_MAPPER);
	}

	public List<HydraTestDetail> getHydraTestDetailsByHydraTestsuiteDetailId (Long hydraTestsuiteDetailId)
	{
		String sql = "select * from " + HydraTestDetailDB.getTableName() + " where " + HydraTestDetailDB.COLUMNS.HYDRA_TESTSUITE_DETAIL_ID.getColumnName() + " = ? ";
		return this.jdbcOperations.query (sql, new Object[] { hydraTestsuiteDetailId }, HydraTestDetailDB.ROW_MAPPER);
	}


	/* START Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

	/* END Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

}
