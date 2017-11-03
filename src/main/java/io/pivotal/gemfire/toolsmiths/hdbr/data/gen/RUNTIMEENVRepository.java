package io.pivotal.gemfire.toolsmiths.hdbr.data.gen;

import com.nurkiewicz.jdbcrepository.JdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.db.RuntimeEnvDB;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.RuntimeEnv;
import java.util.List;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.db.RUNTIMEENVDB;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.RUNTIMEENV;
import com.nurkiewicz.jdbcrepository.TableDescription;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
@Repository
public class RuntimeEnvRepository extends JdbcRepository<RuntimeEnv, String>
{

	final static Logger logger = LoggerFactory.getLogger (RuntimeEnvRepository.class);

	@Autowired
	private JdbcOperations jdbcOperations;

	protected JdbcOperations getJdbcOperations()
	{
		return this.jdbcOperations;
	}

	public RuntimeEnvRepository()
	{
		super (RuntimeEnvDB.ROW_MAPPER, RuntimeEnvDB.ROW_UNMAPPER, RuntimeEnvDB.getTableName (),RuntimeEnvDB.COLUMNS.ID.getColumnName());
	}

	@Override
	protected RuntimeEnv postCreate(RuntimeEnv entity, Number generatedId)
	{
		entity.setId(generatedId.intValue());
		entity.setPersisted(true);
		return entity;
	}


	public List<RuntimeEnv> getRuntimeEnvsByUserName (Long userName)
	{
		String sql = "select * from " + RuntimeEnvDB.getTableName() + " where " + RuntimeEnvDB.COLUMNS.USER_NAME.getColumnName() + " = ? ";
		return this.jdbcOperations.query (sql, new Object[] { userName }, RuntimeEnvDB.ROW_MAPPER);
	}


	/* START Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

	/* END Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

}

