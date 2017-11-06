package io.pivotal.gemfire.toolsmiths.hdbr.data.gen;

import io.pivotal.gemfire.toolsmiths.hdbr.data.JdbcRepository;
import io.pivotal.gemfire.toolsmiths.hdbr.data.RowUnmapper;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.db.HydraReportTagsDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
@Repository
public class HydraReportTagsRepository extends JdbcRepository<HydraReportTags, Integer>
{

	final static Logger logger = LoggerFactory.getLogger (HydraReportTagsRepository.class);

	@Autowired
	private JdbcOperations jdbcOperations;

	protected JdbcOperations getJdbcOperations()
	{
		return this.jdbcOperations;
	}

	public HydraReportTagsRepository()
	{
		super (HydraReportTagsDB.ROW_MAPPER, HydraReportTagsDB.ROW_UNMAPPER, HydraReportTagsDB.getTableName ());
	}

	public HydraReportTagsRepository(RowMapper<HydraReportTags> rowMapper, RowUnmapper<HydraReportTags> rowUnmapper, String idColumn)
	{
		super (HydraReportTagsDB.ROW_MAPPER, HydraReportTagsDB.ROW_UNMAPPER, HydraReportTagsDB.getTableName (), idColumn);
	}

	@Override
	protected HydraReportTags postCreate(HydraReportTags entity, Number generatedId)
	{
		entity.setId(generatedId.intValue());
		entity.setPersisted(true);
		return entity;
	}



	/* START Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

	/* END Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

}

