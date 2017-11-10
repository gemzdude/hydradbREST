--
-- created by running pg_dump -s

-- export PGDATABASE=gregp
-- export PGUSER=gregp
-- export PGHOST=hydrogen.gemstone.com

-- source db was:
-- spring.datasource.url=jdbc:postgresql://hydrogen.gemstone.com/gregp
-- spring.datasource.username=gregp
-- spring.datasource.password=Q6to2ZFd1j9y
-- spring.datasource.driver-class-name=org.postgresql.Driver
--
--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.8
-- Dumped by pg_dump version 9.6.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: pg_stat_statements; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS pg_stat_statements WITH SCHEMA public;


--
-- Name: EXTENSION pg_stat_statements; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION pg_stat_statements IS 'track execution statistics of all SQL statements executed';


SET search_path = public, pg_catalog;

--
-- Name: textcat_all(text); Type: AGGREGATE; Schema: public; Owner: gregp
--

CREATE AGGREGATE textcat_all(text) (
    SFUNC = textcat,
    STYPE = text,
    INITCOND = ''
);


ALTER AGGREGATE public.textcat_all(text) OWNER TO gregp;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: BASE_ENV; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE "BASE_ENV" (
    id integer NOT NULL,
    user_name character varying(256) NOT NULL,
    name character varying(256),
    checkoutpath character varying(1024),
    outputpath character varying(1024)
);


ALTER TABLE "BASE_ENV" OWNER TO gregp;

--
-- Name: BASE_ENV_id_seq; Type: SEQUENCE; Schema: public; Owner: gregp
--

CREATE SEQUENCE "BASE_ENV_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "BASE_ENV_id_seq" OWNER TO gregp;

--
-- Name: BASE_ENV_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gregp
--

ALTER SEQUENCE "BASE_ENV_id_seq" OWNED BY "BASE_ENV".id;


--
-- Name: RUNTIME_ENV; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE "RUNTIME_ENV" (
    name character varying(256),
    user_name character varying(256),
    build_properties character varying(4096),
    targets character varying(1024),
    buildpath character varying(1024) DEFAULT NULL::character varying,
    resultpath character varying(1024) DEFAULT NULL::character varying
);


ALTER TABLE "RUNTIME_ENV" OWNER TO gregp;

--
-- Name: USER; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE "USER" (
    user_name character varying(256) NOT NULL
);


ALTER TABLE "USER" OWNER TO gregp;

--
-- Name: dunit_run; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE dunit_run (
    id integer NOT NULL,
    user_name text,
    path text,
    sites integer,
    runtime_env integer,
    base_env integer,
    revision text,
    branch text,
    os_name text,
    os_version text,
    java_version text,
    java_vm_version text,
    java_vm_vendor text,
    "time" timestamp without time zone DEFAULT now()
);


ALTER TABLE dunit_run OWNER TO gregp;

--
-- Name: dunit_run_id_seq; Type: SEQUENCE; Schema: public; Owner: gregp
--

CREATE SEQUENCE dunit_run_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dunit_run_id_seq OWNER TO gregp;

--
-- Name: dunit_run_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gregp
--

ALTER SEQUENCE dunit_run_id_seq OWNED BY dunit_run.id;


--
-- Name: dunit_test_class; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE dunit_test_class (
    id integer NOT NULL,
    name text NOT NULL
);


ALTER TABLE dunit_test_class OWNER TO gregp;

--
-- Name: dunit_test_class_id_seq; Type: SEQUENCE; Schema: public; Owner: gregp
--

CREATE SEQUENCE dunit_test_class_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dunit_test_class_id_seq OWNER TO gregp;

--
-- Name: dunit_test_class_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gregp
--

ALTER SEQUENCE dunit_test_class_id_seq OWNED BY dunit_test_class.id;


--
-- Name: dunit_test_method; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE dunit_test_method (
    id integer NOT NULL,
    test_class_id integer,
    name text NOT NULL
);


ALTER TABLE dunit_test_method OWNER TO gregp;

--
-- Name: dunit_test_method_detail; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE dunit_test_method_detail (
    id integer NOT NULL,
    method_id integer,
    status text,
    error text,
    run_id integer,
    "time" timestamp without time zone DEFAULT now(),
    tookms bigint DEFAULT 0
);


ALTER TABLE dunit_test_method_detail OWNER TO gregp;

--
-- Name: dunit_test_method_detail_id_seq; Type: SEQUENCE; Schema: public; Owner: gregp
--

CREATE SEQUENCE dunit_test_method_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dunit_test_method_detail_id_seq OWNER TO gregp;

--
-- Name: dunit_test_method_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gregp
--

ALTER SEQUENCE dunit_test_method_detail_id_seq OWNED BY dunit_test_method_detail.id;


--
-- Name: dunit_test_method_id_seq; Type: SEQUENCE; Schema: public; Owner: gregp
--

CREATE SEQUENCE dunit_test_method_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dunit_test_method_id_seq OWNER TO gregp;

--
-- Name: dunit_test_method_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gregp
--

ALTER SEQUENCE dunit_test_method_id_seq OWNED BY dunit_test_method.id;


--
-- Name: dunit_test_run_detail; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE dunit_test_run_detail (
    id integer NOT NULL,
    dunit_run_id integer,
    pass boolean,
    message text,
    failcount integer,
    tookms bigint,
    "time" timestamp without time zone DEFAULT now()
);


ALTER TABLE dunit_test_run_detail OWNER TO gregp;

--
-- Name: dunit_test_run_detail_id_seq; Type: SEQUENCE; Schema: public; Owner: gregp
--

CREATE SEQUENCE dunit_test_run_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dunit_test_run_detail_id_seq OWNER TO gregp;

--
-- Name: dunit_test_run_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gregp
--

ALTER SEQUENCE dunit_test_run_detail_id_seq OWNED BY dunit_test_run_detail.id;


--
-- Name: host; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE host (
    id integer NOT NULL,
    name text NOT NULL,
    os_type text,
    os_info text
);


ALTER TABLE host OWNER TO gregp;

--
-- Name: host_id_seq; Type: SEQUENCE; Schema: public; Owner: gregp
--

CREATE SEQUENCE host_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE host_id_seq OWNER TO gregp;

--
-- Name: host_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gregp
--

ALTER SEQUENCE host_id_seq OWNED BY host.id;


--
-- Name: hydra_report_tags; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE hydra_report_tags (
    id integer NOT NULL,
    tag_name text,
    tag_value text,
    active boolean,
    display_text text,
    priority integer
);


ALTER TABLE hydra_report_tags OWNER TO gregp;

--
-- Name: hydra_report_tags_id_seq; Type: SEQUENCE; Schema: public; Owner: gregp
--

CREATE SEQUENCE hydra_report_tags_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hydra_report_tags_id_seq OWNER TO gregp;

--
-- Name: hydra_report_tags_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gregp
--

ALTER SEQUENCE hydra_report_tags_id_seq OWNED BY hydra_report_tags.id;


--
-- Name: hydra_run; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE hydra_run (
    id integer NOT NULL,
    user_name text,
    product_version text,
    build_id text,
    svn_repository text,
    svn_revision text,
    java_version text,
    java_vendor text,
    java_home text,
    date timestamp without time zone DEFAULT now(),
    full_regression boolean,
    regression_type integer,
    comments text,
    build_location text
);


ALTER TABLE hydra_run OWNER TO gregp;

--
-- Name: hydra_run_id_seq; Type: SEQUENCE; Schema: public; Owner: gregp
--

CREATE SEQUENCE hydra_run_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hydra_run_id_seq OWNER TO gregp;

--
-- Name: hydra_run_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gregp
--

ALTER SEQUENCE hydra_run_id_seq OWNED BY hydra_run.id;


--
-- Name: hydra_test; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE hydra_test (
    id integer NOT NULL,
    conf text NOT NULL,
    full_test_spec text,
    hydra_testsuite_id integer
);


ALTER TABLE hydra_test OWNER TO gregp;

--
-- Name: hydra_test_detail; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE hydra_test_detail (
    id integer NOT NULL,
    elapsed_time text,
    disk_usage text,
    status text,
    error text,
    bug_number character varying(15),
    hydra_test_id integer,
    hydra_testsuite_detail_id integer,
    hydra_run_id integer,
    comment text,
    tags text
);


ALTER TABLE hydra_test_detail OWNER TO gregp;

--
-- Name: hydra_test_detail_ext; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE hydra_test_detail_ext (
    id bigint NOT NULL,
    log_location text
);


ALTER TABLE hydra_test_detail_ext OWNER TO gregp;

--
-- Name: hydra_test_detail_id_seq; Type: SEQUENCE; Schema: public; Owner: gregp
--

CREATE SEQUENCE hydra_test_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hydra_test_detail_id_seq OWNER TO gregp;

--
-- Name: hydra_test_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gregp
--

ALTER SEQUENCE hydra_test_detail_id_seq OWNED BY hydra_test_detail.id;


--
-- Name: hydra_test_id_seq; Type: SEQUENCE; Schema: public; Owner: gregp
--

CREATE SEQUENCE hydra_test_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hydra_test_id_seq OWNER TO gregp;

--
-- Name: hydra_test_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gregp
--

ALTER SEQUENCE hydra_test_id_seq OWNED BY hydra_test.id;


--
-- Name: hydra_testsuite; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE hydra_testsuite (
    id integer NOT NULL,
    name text NOT NULL
);


ALTER TABLE hydra_testsuite OWNER TO gregp;

--
-- Name: hydra_testsuite_detail; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE hydra_testsuite_detail (
    id integer NOT NULL,
    date timestamp without time zone DEFAULT now(),
    elapsed_time text,
    disk_usage text,
    passcount integer,
    failcount integer,
    hangcount integer,
    local_conf text,
    hydra_testsuite_id integer,
    hydra_run_id integer,
    host_id integer,
    comment text,
    artifact_location text DEFAULT ''::text
);


ALTER TABLE hydra_testsuite_detail OWNER TO gregp;

--
-- Name: hydra_testsuite_detail_id_seq; Type: SEQUENCE; Schema: public; Owner: gregp
--

CREATE SEQUENCE hydra_testsuite_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hydra_testsuite_detail_id_seq OWNER TO gregp;

--
-- Name: hydra_testsuite_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gregp
--

ALTER SEQUENCE hydra_testsuite_detail_id_seq OWNED BY hydra_testsuite_detail.id;


--
-- Name: hydra_testsuite_id_seq; Type: SEQUENCE; Schema: public; Owner: gregp
--

CREATE SEQUENCE hydra_testsuite_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hydra_testsuite_id_seq OWNER TO gregp;

--
-- Name: hydra_testsuite_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gregp
--

ALTER SEQUENCE hydra_testsuite_id_seq OWNED BY hydra_testsuite.id;


--
-- Name: runtime_env; Type: TABLE; Schema: public; Owner: gregp
--

CREATE TABLE runtime_env (
    name character varying(256),
    user_name character varying(256),
    build_properties character varying(4096),
    targets character varying(1024),
    buildpath character varying(1024) DEFAULT NULL::character varying,
    resultpath character varying(1024) DEFAULT NULL::character varying
);


ALTER TABLE runtime_env OWNER TO gregp;

--
-- Name: BASE_ENV id; Type: DEFAULT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY "BASE_ENV" ALTER COLUMN id SET DEFAULT nextval('"BASE_ENV_id_seq"'::regclass);


--
-- Name: dunit_run id; Type: DEFAULT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY dunit_run ALTER COLUMN id SET DEFAULT nextval('dunit_run_id_seq'::regclass);


--
-- Name: dunit_test_class id; Type: DEFAULT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY dunit_test_class ALTER COLUMN id SET DEFAULT nextval('dunit_test_class_id_seq'::regclass);


--
-- Name: dunit_test_method id; Type: DEFAULT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY dunit_test_method ALTER COLUMN id SET DEFAULT nextval('dunit_test_method_id_seq'::regclass);


--
-- Name: dunit_test_method_detail id; Type: DEFAULT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY dunit_test_method_detail ALTER COLUMN id SET DEFAULT nextval('dunit_test_method_detail_id_seq'::regclass);


--
-- Name: dunit_test_run_detail id; Type: DEFAULT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY dunit_test_run_detail ALTER COLUMN id SET DEFAULT nextval('dunit_test_run_detail_id_seq'::regclass);


--
-- Name: host id; Type: DEFAULT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY host ALTER COLUMN id SET DEFAULT nextval('host_id_seq'::regclass);


--
-- Name: hydra_report_tags id; Type: DEFAULT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_report_tags ALTER COLUMN id SET DEFAULT nextval('hydra_report_tags_id_seq'::regclass);


--
-- Name: hydra_run id; Type: DEFAULT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_run ALTER COLUMN id SET DEFAULT nextval('hydra_run_id_seq'::regclass);


--
-- Name: hydra_test id; Type: DEFAULT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_test ALTER COLUMN id SET DEFAULT nextval('hydra_test_id_seq'::regclass);


--
-- Name: hydra_test_detail id; Type: DEFAULT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_test_detail ALTER COLUMN id SET DEFAULT nextval('hydra_test_detail_id_seq'::regclass);


--
-- Name: hydra_testsuite id; Type: DEFAULT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_testsuite ALTER COLUMN id SET DEFAULT nextval('hydra_testsuite_id_seq'::regclass);


--
-- Name: hydra_testsuite_detail id; Type: DEFAULT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_testsuite_detail ALTER COLUMN id SET DEFAULT nextval('hydra_testsuite_detail_id_seq'::regclass);


--
-- Name: BASE_ENV BASE_ENV_pkey; Type: CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY "BASE_ENV"
    ADD CONSTRAINT "BASE_ENV_pkey" PRIMARY KEY (id);


--
-- Name: hydra_test_detail_ext ID_PK_1; Type: CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_test_detail_ext
    ADD CONSTRAINT "ID_PK_1" PRIMARY KEY (id);


--
-- Name: USER USER_pkey; Type: CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY "USER"
    ADD CONSTRAINT "USER_pkey" PRIMARY KEY (user_name);


--
-- Name: dunit_run dunit_run_pkey; Type: CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY dunit_run
    ADD CONSTRAINT dunit_run_pkey PRIMARY KEY (id);


--
-- Name: dunit_test_class dunit_test_class_pkey; Type: CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY dunit_test_class
    ADD CONSTRAINT dunit_test_class_pkey PRIMARY KEY (id);


--
-- Name: dunit_test_method_detail dunit_test_method_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY dunit_test_method_detail
    ADD CONSTRAINT dunit_test_method_detail_pkey PRIMARY KEY (id);


--
-- Name: dunit_test_method dunit_test_method_pkey; Type: CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY dunit_test_method
    ADD CONSTRAINT dunit_test_method_pkey PRIMARY KEY (id);


--
-- Name: dunit_test_run_detail dunit_test_run_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY dunit_test_run_detail
    ADD CONSTRAINT dunit_test_run_detail_pkey PRIMARY KEY (id);


--
-- Name: host host_pkey; Type: CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY host
    ADD CONSTRAINT host_pkey PRIMARY KEY (id);


--
-- Name: hydra_report_tags hydra_report_tag_pkey; Type: CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_report_tags
    ADD CONSTRAINT hydra_report_tag_pkey PRIMARY KEY (id);


--
-- Name: hydra_run hydra_run_pkey; Type: CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_run
    ADD CONSTRAINT hydra_run_pkey PRIMARY KEY (id);


--
-- Name: hydra_test_detail hydra_test_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_test_detail
    ADD CONSTRAINT hydra_test_detail_pkey PRIMARY KEY (id);


--
-- Name: hydra_test hydra_test_pkey; Type: CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_test
    ADD CONSTRAINT hydra_test_pkey PRIMARY KEY (id);


--
-- Name: hydra_testsuite_detail hydra_testsuite_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_testsuite_detail
    ADD CONSTRAINT hydra_testsuite_detail_pkey PRIMARY KEY (id);


--
-- Name: hydra_testsuite hydra_testsuite_pkey; Type: CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_testsuite
    ADD CONSTRAINT hydra_testsuite_pkey PRIMARY KEY (id);


--
-- Name: hydra_test_detail_hydra_run_id_idx; Type: INDEX; Schema: public; Owner: gregp
--

CREATE INDEX hydra_test_detail_hydra_run_id_idx ON hydra_test_detail USING btree (hydra_run_id);


--
-- Name: hydra_test_detail_hydra_test_id_idx; Type: INDEX; Schema: public; Owner: gregp
--

CREATE INDEX hydra_test_detail_hydra_test_id_idx ON hydra_test_detail USING btree (hydra_test_id);


--
-- Name: hydra_test_detail_hydra_testsuite_detail_id_idx; Type: INDEX; Schema: public; Owner: gregp
--

CREATE INDEX hydra_test_detail_hydra_testsuite_detail_id_idx ON hydra_test_detail USING btree (hydra_testsuite_detail_id);


--
-- Name: hydra_testsuite_name_dx; Type: INDEX; Schema: public; Owner: gregp
--

CREATE INDEX hydra_testsuite_name_dx ON hydra_testsuite USING btree (name);


--
-- Name: idandclass; Type: INDEX; Schema: public; Owner: gregp
--

CREATE INDEX idandclass ON dunit_test_method USING btree (id, test_class_id);


--
-- Name: BASE_ENV BASE_ENV_user_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY "BASE_ENV"
    ADD CONSTRAINT "BASE_ENV_user_name_fkey" FOREIGN KEY (user_name) REFERENCES "USER"(user_name);


--
-- Name: hydra_test_detail_ext ID_FK_1; Type: FK CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_test_detail_ext
    ADD CONSTRAINT "ID_FK_1" FOREIGN KEY (id) REFERENCES hydra_test_detail(id);


--
-- Name: RUNTIME_ENV RUNTIME_ENV_user_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY "RUNTIME_ENV"
    ADD CONSTRAINT "RUNTIME_ENV_user_name_fkey" FOREIGN KEY (user_name) REFERENCES "USER"(user_name);


--
-- Name: dunit_test_method_detail dunit_test_method_detail_run_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY dunit_test_method_detail
    ADD CONSTRAINT dunit_test_method_detail_run_id_fkey FOREIGN KEY (run_id) REFERENCES dunit_run(id);


--
-- Name: dunit_test_method dunit_test_method_test_class_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY dunit_test_method
    ADD CONSTRAINT dunit_test_method_test_class_id_fkey FOREIGN KEY (test_class_id) REFERENCES dunit_test_class(id);


--
-- Name: dunit_test_run_detail dunit_test_run_detail_dunit_run_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY dunit_test_run_detail
    ADD CONSTRAINT dunit_test_run_detail_dunit_run_id_fkey FOREIGN KEY (dunit_run_id) REFERENCES dunit_run(id);


--
-- Name: hydra_test_detail hydra_test_detail_hydra_run_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_test_detail
    ADD CONSTRAINT hydra_test_detail_hydra_run_id_fkey FOREIGN KEY (hydra_run_id) REFERENCES hydra_run(id);


--
-- Name: hydra_test_detail hydra_test_detail_hydra_test_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_test_detail
    ADD CONSTRAINT hydra_test_detail_hydra_test_id_fkey FOREIGN KEY (hydra_test_id) REFERENCES hydra_test(id);


--
-- Name: hydra_test_detail hydra_test_detail_hydra_testsuite_detail_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_test_detail
    ADD CONSTRAINT hydra_test_detail_hydra_testsuite_detail_id_fkey FOREIGN KEY (hydra_testsuite_detail_id) REFERENCES hydra_testsuite_detail(id);


--
-- Name: hydra_test hydra_test_hydra_testsuite_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_test
    ADD CONSTRAINT hydra_test_hydra_testsuite_id_fkey FOREIGN KEY (hydra_testsuite_id) REFERENCES hydra_testsuite(id);


--
-- Name: hydra_testsuite_detail hydra_testsuite_detail_host_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_testsuite_detail
    ADD CONSTRAINT hydra_testsuite_detail_host_id_fkey FOREIGN KEY (host_id) REFERENCES host(id);


--
-- Name: hydra_testsuite_detail hydra_testsuite_detail_hydra_run_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_testsuite_detail
    ADD CONSTRAINT hydra_testsuite_detail_hydra_run_id_fkey FOREIGN KEY (hydra_run_id) REFERENCES hydra_run(id);


--
-- Name: hydra_testsuite_detail hydra_testsuite_detail_hydra_testsuite_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY hydra_testsuite_detail
    ADD CONSTRAINT hydra_testsuite_detail_hydra_testsuite_id_fkey FOREIGN KEY (hydra_testsuite_id) REFERENCES hydra_testsuite(id);


--
-- Name: runtime_env runtime_env_user_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: gregp
--

ALTER TABLE ONLY runtime_env
    ADD CONSTRAINT runtime_env_user_name_fkey FOREIGN KEY (user_name) REFERENCES "USER"(user_name);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

