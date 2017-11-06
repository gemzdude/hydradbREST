package io.pivotal.gemfire.toolsmiths.hdbr.data.gen;

import io.pivotal.gemfire.toolsmiths.hdbr.data.JdbcRepository;
import io.pivotal.gemfire.toolsmiths.hdbr.data.RowUnmapper;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.db.BASEENVDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
@Repository
public class BASEENVRepository extends JdbcRepository<BASEENV, Integer>
{

	final static Logger logger = LoggerFactory.getLogger (BASEENVRepository.class);

	@Autowired
	private JdbcOperations jdbcOperations;

	protected JdbcOperations getJdbcOperations()
	{
		return this.jdbcOperations;
	}

	public BASEENVRepository()
	{
		super (BASEENVDB.ROW_MAPPER, BASEENVDB.ROW_UNMAPPER, BASEENVDB.getTableName ());
	}

	public BASEENVRepository(RowMapper<BASEENV> rowMapper, RowUnmapper<BASEENV> rowUnmapper, String idColumn)
	{
		super (BASEENVDB.ROW_MAPPER, BASEENVDB.ROW_UNMAPPER, BASEENVDB.getTableName (), idColumn);
	}

	@Override
	protected BASEENV postCreate(BASEENV entity, Number generatedId)
	{
		entity.setId(generatedId.intValue());
		entity.setPersisted(true);
		return entity;
	}


	public List<BASEENV> getBASEENVsByUserName (Long userName)
	{
		String sql = "select * from " + BASEENVDB.getTableName() + " where " + BASEENVDB.COLUMNS.USER_NAME.getColumnName() + " = ? ";
		return this.jdbcOperations.query (sql, new Object[] { userName }, BASEENVDB.ROW_MAPPER);
	}


	/* START Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

	/* END Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

}

