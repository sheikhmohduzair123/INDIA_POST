--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5
-- Dumped by pg_dump version 11.5

-- Started on 2019-09-25 13:03:48 IST

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
-- TOC entry 220 (class 1259 OID 16611)
-- Name: district; Type: TABLE; Schema: public; Owner: shikhargupta
--

CREATE TABLE public.district (
    id bigint DEFAULT NOT NULL,
    createdon timestamp without time zone,
    status integer,
    district character varying(255)
);


ALTER TABLE public.district OWNER TO postgres;

--
-- TOC entry 3270 (class 0 OID 16611)
-- Dependencies: 220
-- Data for Name: district; Type: TABLE DATA; Schema: public; Owner: shikhargupta
--

COPY public.district (id, createdon, status, district) FROM stdin;
1	2019-09-25 12:50:06.91725	1	Gopalganj
2	2019-09-25 12:50:06.91725	1	Moulvibazar
3	2019-09-25 12:50:06.91725	1	Magura
4	2019-09-25 12:50:06.91725	1	Kishoreganj
5	2019-09-25 12:50:06.91725	1	Jamalpur
6	2019-09-25 12:50:06.91725	1	Jhalokati
7	2019-09-25 12:50:06.91725	1	Sherpur
8	2019-09-25 12:50:06.91725	1	Munshiganj
9	2019-09-25 12:50:06.91725	1	Jhenaidah
10	2019-09-25 12:50:06.91725	1	Netrakona
11	2019-09-25 12:50:06.91725	1	Mymensingh
12	2019-09-25 12:50:06.91725	1	Jashore
13	2019-09-25 12:50:06.91725	1	Pabna
14	2019-09-25 12:50:06.91725	1	Rajshahi
15	2019-09-25 12:50:06.91725	1	Narsingdi
16	2019-09-25 12:50:06.91725	1	Tangail
17	2019-09-25 12:50:06.91725	1	Habiganj
18	2019-09-25 12:50:06.91725	1	Madaripur
19	2019-09-25 12:50:06.91725	1	Naogaon
20	2019-09-25 12:50:06.91725	1	Panchagarh
21	2019-09-25 12:50:06.91725	1	Natore
22	2019-09-25 12:50:06.91725	1	Meherpur
23	2019-09-25 12:50:06.91725	1	Gazipur
24	2019-09-25 12:50:06.91725	1	Nilphamari
25	2019-09-25 12:50:06.91725	1	Kurigram
26	2019-09-25 12:50:06.91725	1	Dinajpur
27	2019-09-25 12:50:06.91725	1	Chapai Nawabganj
28	2019-09-25 12:50:06.91725	1	Manikganj
29	2019-09-25 12:50:06.91725	1	Chuadanga
30	2019-09-25 12:50:06.91725	1	Narail
31	2019-09-25 12:50:06.91725	1	Bagerhat
32	2019-09-25 12:50:06.91725	1	Kushtia
33	2019-09-25 12:50:06.91725	1	Satkhira
34	2019-09-25 12:50:06.91725	1	Bhola
35	2019-09-25 12:50:06.91725	1	Barguna
36	2019-09-25 12:50:06.91725	1	Dhaka
37	2019-09-25 12:50:06.91725	1	Gaibandha
38	2019-09-25 12:50:06.91725	1	Sunamganj
39	2019-09-25 12:50:06.91725	1	Faridpur
40	2019-09-25 12:50:06.91725	1	Rajbari
41	2019-09-25 12:50:06.91725	1	Barishal
42	2019-09-25 12:50:06.91725	1	Bogura
43	2019-09-25 12:50:06.91725	1	Khulna
44	2019-09-25 12:50:06.91725	1	Lalmonirhat
45	2019-09-25 12:50:06.91725	1	Rangpur
46	2019-09-25 12:50:06.91725	1	Pirojpur
47	2019-09-25 12:50:06.91725	1	Shariatpur
48	2019-09-25 12:50:06.91725	1	Sylhet
49	2019-09-25 12:50:06.91725	1	Narayanganj
50	2019-09-25 12:50:06.91725	1	Sirajganj
51	2019-09-25 12:50:06.91725	1	Joypurhat
52	2019-09-25 12:50:06.91725	1	Thakurgaon
53	2019-09-25 12:50:06.91725	1	Patuakhali
54	2019-09-25 12:50:06.91725	1	Dhaka 
\.


--
-- TOC entry 3148 (class 2606 OID 16615)
-- Name: district district_pkey; Type: CONSTRAINT; Schema: public; Owner: shikhargupta
--

ALTER TABLE ONLY public.district
    ADD CONSTRAINT district_pkey PRIMARY KEY (id);


-- Completed on 2019-09-25 13:03:49 IST

--
-- PostgreSQL database dump complete
--

