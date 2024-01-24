--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5
-- Dumped by pg_dump version 11.5

-- Started on 2019-09-25 13:08:03 IST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 224 (class 1259 OID 16631)
-- Name: zone; Type: TABLE; Schema: public; Owner: shikhargupta
--

CREATE TABLE public.zone (
    id bigint NOT NULL,
    createdon timestamp without time zone,
    status integer,
    zone character varying(255)
);


ALTER TABLE public.zone OWNER TO postgres;

--
-- TOC entry 3269 (class 0 OID 16631)
-- Dependencies: 224
-- Data for Name: zone; Type: TABLE DATA; Schema: public; Owner: shikhargupta
--

COPY public.zone (id, createdon, status, zone) FROM stdin;
1	2019-09-25 12:56:41.825018	1	dummyZone
\.


--
-- TOC entry 3147 (class 2606 OID 16635)
-- Name: zone zone_pkey; Type: CONSTRAINT; Schema: public; Owner: shikhargupta
--

ALTER TABLE ONLY public.zone
    ADD CONSTRAINT zone_pkey PRIMARY KEY (id);


-- Completed on 2019-09-25 13:08:03 IST

--
-- PostgreSQL database dump complete
--

