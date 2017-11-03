package io.pivotal.gemfire.toolsmiths.hdbr.data.gen;

import com.nurkiewicz.jdbcrepository.JdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.db.HydraTestsuiteDB;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraTestsuite;
import org.springframework.jdbc.core.RowMapper;
import com.nurkiewicz.jdbcrepository.RowUnmapper;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
@Repository
public class HydraTestsuiteRepository extends JdbcRepository<HydraTestsuite, Integer>
{

	final static Logger logger = LoggerFactory.getLogger (HydraTestsuiteRepository.class);

	@Autowired
	private JdbcOperations jdbcOperations;

	protected JdbcOperations getJdbcOperations()
	{
		return this.jdbcOperations;
	}

	public HydraTestsuiteRepository()
	{
		super (HydraTestsuiteDB.ROW_MAPPER, HydraTestsuiteDB.ROW_UNMAPPER, HydraTestsuiteDB.getTableName ());
	}

	public HydraTestsuiteRepository(RowMapper<HydraTestsuite> rowMapper, RowUnmapper<HydraTestsuite> rowUnmapper, String idColumn)
	{
		super (HydraTestsuiteDB.ROW_MAPPER, HydraTestsuiteDB.ROW_UNMAPPER, HydraTestsuiteDB.getTableName (), idColumn);
	}

	@Override
	protected HydraTestsuite postCreate(HydraTestsuite entity, Number generatedId)
	{
		entity.setId(generatedId.intValue());
		entity.setPersisted(true);
		return entity;
	}



	/* START Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

	/* END Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

}
