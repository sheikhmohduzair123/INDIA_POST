--
-- PostgreSQL database dump
--

-- Dumped from database version 10.10
-- Dumped by pg_dump version 10.10

-- Started on 2019-09-25 11:59:39

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
-- TOC entry 214 (class 1259 OID 24689)
-- Name: division; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.division (
    id bigint NOT NULL,
    createdon timestamp without time zone,
    status integer,
    division character varying(255)
);


ALTER TABLE public.division OWNER TO postgres;

--
-- TOC entry 2857 (class 0 OID 24689)
-- Dependencies: 214
-- Data for Name: division; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.division (id, createdon, status, division) FROM stdin;
1	2019-09-25 11:00:26.563416	0	Dhaka
2	2019-09-25 11:00:26.563416	0	Mymensingh
3	2019-09-25 11:00:26.563416	0	Sylhet
4	2019-09-25 11:00:26.563416	0	Rangpur
5	2019-09-25 11:00:26.563416	0	Rajshahi
6	2019-09-25 11:00:26.563416	0	Barishal
7	2019-09-25 11:00:26.563416	0	Khulna
\.


--
-- TOC entry 2735 (class 2606 OID 24693)
-- Name: division division_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.division
    ADD CONSTRAINT division_pkey PRIMARY KEY (id);


-- Completed on 2019-09-25 11:59:39

--
-- PostgreSQL database dump complete
--

