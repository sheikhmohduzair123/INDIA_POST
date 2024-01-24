--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5
-- Dumped by pg_dump version 11.5

-- Started on 2019-09-25 13:07:15 IST

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
-- TOC entry 223 (class 1259 OID 16626)
-- Name: thana; Type: TABLE; Schema: public; Owner: shikhargupta
--

CREATE TABLE public.thana (
    id bigint NOT NULL,
    createdon timestamp without time zone,
    status integer,
    thana character varying(255)
);


ALTER TABLE public.thana OWNER TO postgres;

--
-- TOC entry 3270 (class 0 OID 16626)
-- Dependencies: 223
-- Data for Name: thana; Type: TABLE DATA; Schema: public; Owner: shikhargupta
--

COPY public.thana (id, createdon, status, thana) FROM stdin;
1	2019-09-25 12:53:06.668066	1	Singra
2	2019-09-25 12:53:06.668066	1	Chitalmari
3	2019-09-25 12:53:06.668066	1	Dupchachia
4	2019-09-25 12:53:06.668066	1	Pirganj
5	2019-09-25 12:53:06.668066	1	Ulipur
6	2019-09-25 12:53:06.668066	1	Kalmakanda
7	2019-09-25 12:53:06.668066	1	Muktagachha
8	2019-09-25 12:53:06.668066	1	Doulatpur
9	2019-09-25 12:53:06.668066	1	Srimangal
10	2019-09-25 12:53:06.668066	1	Tangail Sadar
11	2019-09-25 12:53:06.668066	1	Nawabganj
12	2019-09-25 12:53:06.668066	1	Maksudpur
13	2019-09-25 12:53:06.668066	1	Rafayetpur
14	2019-09-25 12:53:06.668066	1	Dhangora
15	2019-09-25 12:53:06.668066	1	Taala
16	2019-09-25 12:53:06.668066	1	Kendua
17	2019-09-25 12:53:06.668066	1	Bhairob
18	2019-09-25 12:53:06.668066	1	Bandar
19	2019-09-25 12:53:06.668066	1	Sirajdikhan
20	2019-09-25 12:53:06.668066	1	Dhanmondi
21	2019-09-25 12:53:06.668066	1	Malandah
22	2019-09-25 12:53:06.668066	1	Kumarkhali
23	2019-09-25 12:53:06.668066	1	Kathalia
24	2019-09-25 12:53:06.668066	1	Bhola Sadar
25	2019-09-25 12:53:06.668066	1	Itna
26	2019-09-25 12:53:06.668066	1	Amtali
27	2019-09-25 12:53:06.668066	1	Sonatola
28	2019-09-25 12:53:06.668066	1	Trishal
29	2019-09-25 12:53:06.668066	1	Susung Durgapur
30	2019-09-25 12:53:06.668066	1	Kalihati
31	2019-09-25 12:53:06.668066	1	Belabo
32	2019-09-25 12:53:06.668066	1	Magura Sadar
33	2019-09-25 12:53:06.668066	1	Chuadanga Sadar
34	2019-09-25 12:53:06.668066	1	Rupganj
35	2019-09-25 12:53:06.668066	1	Pakundia
36	2019-09-25 12:53:06.668066	1	Sariakandi
37	2019-09-25 12:53:06.668066	1	Gopalganj
38	2019-09-25 12:53:06.668066	1	Birganj
39	2019-09-25 12:53:06.668066	1	Durgapur
40	2019-09-25 12:53:06.668066	1	Islampur
41	2019-09-25 12:53:06.668066	1	Bholahat
42	2019-09-25 12:53:06.668066	1	Dharmapasha
43	2019-09-25 12:53:06.668066	1	Noapara
44	2019-09-25 12:53:06.668066	1	Bera
45	2019-09-25 12:53:06.668066	1	Basail
46	2019-09-25 12:53:06.668066	1	Baidder Bazar
47	2019-09-25 12:53:06.668066	1	Mahadebpur
48	2019-09-25 12:53:06.668066	1	Manikganj Sadar
49	2019-09-25 12:53:06.668066	1	Gopalpur UPO
50	2019-09-25 12:53:06.668066	1	Bagerhat Sadar
51	2019-09-25 12:53:06.668066	1	Fenchuganj
52	2019-09-25 12:53:06.668066	1	Bakshigonj
53	2019-09-25 12:53:06.668066	1	kalaroa
54	2019-09-25 12:53:06.668066	1	Hatshoshiganj
55	2019-09-25 12:53:06.668066	1	Gheor
56	2019-09-25 12:53:06.668066	1	Satkhira Sadar
57	2019-09-25 12:53:06.668066	1	Bhaluka
58	2019-09-25 12:53:06.668066	1	Nabiganj
59	2019-09-25 12:53:06.668066	1	Kurigram Sadar
60	2019-09-25 12:53:06.668066	1	Debbhata
61	2019-09-25 12:53:06.668066	1	Badalgachhi
62	2019-09-25 12:53:06.668066	1	Dabiganj
63	2019-09-25 12:53:06.668066	1	Sarsa
64	2019-09-25 12:53:06.668066	1	Chilmari
65	2019-09-25 12:53:06.668066	1	Akkelpur
66	2019-09-25 12:53:06.668066	1	Sirajganj Sadar
67	2019-09-25 12:53:06.668066	1	Araihazar
68	2019-09-25 12:53:06.668066	1	Bagharpara
69	2019-09-25 12:53:06.668066	1	Atpara
70	2019-09-25 12:53:06.668066	1	Agailzhara
71	2019-09-25 12:53:06.668066	1	Bhabaniganj
72	2019-09-25 12:53:06.668066	1	Singari
73	2019-09-25 12:53:06.668066	1	Mirpur
74	2019-09-25 12:53:06.668066	1	Raypura
75	2019-09-25 12:53:06.668066	1	Doulatkhan
76	2019-09-25 12:53:06.668066	1	Nitpur
77	2019-09-25 12:53:06.668066	1	Balaganjgfhfghj
78	2019-09-25 12:53:06.668066	1	Azmireeganj
79	2019-09-25 12:53:06.668066	1	Kashkaolia
80	2019-09-25 12:53:06.668066	1	Banwarinagar
81	2019-09-25 12:53:06.668066	1	Kotchandpur
82	2019-09-25 12:53:06.668066	1	Prasadpur
83	2019-09-25 12:53:06.668066	1	Kishoreganj Sadar
84	2019-09-25 12:53:06.668066	1	Fulbaria
85	2019-09-25 12:53:06.668066	1	Biral
86	2019-09-25 12:53:06.668066	1	Dimla
87	2019-09-25 12:53:06.668066	1	Jajira
88	2019-09-25 12:53:06.668066	1	Phulpur
89	2019-09-25 12:53:06.668066	1	Bianibazar
90	2019-09-25 12:53:06.668066	1	Niamatpur
91	2019-09-25 12:53:06.668066	1	Rayenda
92	2019-09-25 12:53:06.668066	1	Khilgaon
93	2019-09-25 12:53:06.668066	1	Dinajpur Sadar
94	2019-09-25 12:53:06.668066	1	Nikli
95	2019-09-25 12:53:06.668066	1	Boalmari
96	2019-09-25 12:53:06.668066	1	Barhamganj
97	2019-09-25 12:53:06.668066	1	Gaibandha Sadar
98	2019-09-25 12:53:06.668066	1	Monnunagar
99	2019-09-25 12:53:06.668066	1	Putia
100	2019-09-25 12:53:06.668066	1	Uzirpur
101	2019-09-25 12:53:06.668066	1	Rani Sankail
102	2019-09-25 12:53:06.668066	1	Nageshwar
103	2019-09-25 12:53:06.668066	1	Bhuapur
104	2019-09-25 12:53:06.668066	1	Dupchanchia
105	2019-09-25 12:53:06.668066	1	Naogaon Sadar
106	2019-09-25 12:53:06.668066	1	Lalitganj
107	2019-09-25 12:53:06.668066	1	Gabtoli
108	2019-09-25 12:53:06.668066	1	Kaunia
109	2019-09-25 12:53:06.668066	1	Kaliganj UPO
110	2019-09-25 12:53:06.668066	1	Chalna Bazar
111	2019-09-25 12:53:06.668066	1	Siddirganj
112	2019-09-25 12:53:06.668066	1	Nagarpur
113	2019-09-25 12:53:06.668066	1	Rajnagar
114	2019-09-25 12:53:06.668066	1	Narail Sadar
115	2019-09-25 12:53:06.668066	1	Gouranadi
116	2019-09-25 12:53:06.668066	1	Charghat
117	2019-09-25 12:53:06.668066	1	Bangla Hili
118	2019-09-25 12:53:06.668066	1	Gangni
119	2019-09-25 12:53:06.668066	1	Mohanganj
120	2019-09-25 12:53:06.668066	1	Tarial
121	2019-09-25 12:53:06.668066	1	Ostagram
122	2019-09-25 12:53:06.668066	1	Dhaka 
123	2019-09-25 12:53:06.668066	1	Bhanga
124	2019-09-25 12:53:06.668066	1	Arpara
125	2019-09-25 12:53:06.668066	1	Uttara
126	2019-09-25 12:53:06.668066	1	Rajapur
127	2019-09-25 12:53:06.668066	1	Mithamoin
128	2019-09-25 12:53:06.668066	1	kalkini
129	2019-09-25 12:53:06.668066	1	Dhirai Chandpur
130	2019-09-25 12:53:06.668066	1	Nazirpur
131	2019-09-25 12:53:06.668066	1	Ghatail
132	2019-09-25 12:53:06.668066	1	Rangpur Sadar
133	2019-09-25 12:53:06.668066	1	Rajarhat
134	2019-09-25 12:53:06.668066	1	Pangsha
135	2019-09-25 12:53:06.668066	1	Rampal
136	2019-09-25 12:53:06.668066	1	Phulbari
137	2019-09-25 12:53:06.668066	1	Narsingdi Sadar
138	2019-09-25 12:53:06.668066	1	Tahirpur
139	2019-09-25 12:53:06.668066	1	Nakla
140	2019-09-25 12:53:06.668066	1	Ashashuni
141	2019-09-25 12:53:06.668066	1	Kachua UPO
142	2019-09-25 12:53:06.668066	1	Barishal Sadar
143	2019-09-25 12:53:06.668066	1	Patgram
144	2019-09-25 12:53:06.668066	1	Kaliganj
145	2019-09-25 12:53:06.668066	1	Shriangan
146	2019-09-25 12:53:06.668066	1	Gobindaganj
147	2019-09-25 12:53:06.668066	1	Kapashia
148	2019-09-25 12:53:06.668066	1	Naldanga
149	2019-09-25 12:53:06.668066	1	Dhamuirhat
150	2019-09-25 12:53:06.668066	1	Bogura Sadar
151	2019-09-25 12:53:06.668066	1	kalai
152	2019-09-25 12:53:06.668066	1	Joypurhat Sadar
153	2019-09-25 12:53:06.668066	1	Banaripara
154	2019-09-25 12:53:06.668066	1	Bhangura
155	2019-09-25 12:53:06.668066	1	Khulna Sadar
156	2019-09-25 12:53:06.668066	1	Munshiganj Sadar
157	2019-09-25 12:53:06.668066	1	Ghoraghat
158	2019-09-25 12:53:06.668066	1	Faridpur Sadar
159	2019-09-25 12:53:06.668066	1	Mahendiganj
160	2019-09-25 12:53:06.668066	1	Sapahar
161	2019-09-25 12:53:06.668066	1	Tejgaon
162	2019-09-25 12:53:06.668066	1	Sundarganj
163	2019-09-25 12:53:06.668066	1	Shibpur
164	2019-09-25 12:53:06.668066	1	Barajalia
165	2019-09-25 12:53:06.668066	1	Gosairhat
166	2019-09-25 12:53:06.668066	1	Saadullapur
167	2019-09-25 12:53:06.668066	1	Kanaighat
168	2019-09-25 12:53:06.668066	1	Maharajganj
169	2019-09-25 12:53:06.668066	1	Ramna
170	2019-09-25 12:53:06.668066	1	Damurhuda
171	2019-09-25 12:53:06.668066	1	Rohanpur
172	2019-09-25 12:53:06.668066	1	Gopalpur
173	2019-09-25 12:53:06.668066	1	Kashiani
174	2019-09-25 12:53:06.668066	1	Pirgachha
175	2019-09-25 12:53:06.668066	1	Ishwardi
176	2019-09-25 12:53:06.668066	1	Baliadangi
177	2019-09-25 12:53:06.668066	1	Sajiara
178	2019-09-25 12:53:06.668066	1	Baniachang
179	2019-09-25 12:53:06.668066	1	Bajitpur
180	2019-09-25 12:53:06.668066	1	Bhedorganj
181	2019-09-25 12:53:06.668066	1	Gopalganj Sadar
182	2019-09-25 12:53:06.668066	1	Bhurungamari
183	2019-09-25 12:53:06.668066	1	Hatgurudaspur
184	2019-09-25 12:53:06.668066	1	Rajbari Sadar
185	2019-09-25 12:53:06.668066	1	Monohordi
186	2019-09-25 12:53:06.668066	1	Habiganj Sadar
187	2019-09-25 12:53:06.668066	1	Patnitala
188	2019-09-25 12:53:06.668066	1	Birampur
189	2019-09-25 12:53:06.668066	1	Chapai Nawabganj Sadar
190	2019-09-25 12:53:06.668066	1	Madhabpur
191	2019-09-25 12:53:06.668066	1	Adamdighi
192	2019-09-25 12:53:06.668066	1	Khetlal
193	2019-09-25 12:53:06.668066	1	Dashmina
194	2019-09-25 12:53:06.668066	1	Khansama
195	2019-09-25 12:53:06.668066	1	Rajoir
196	2019-09-25 12:53:06.668066	1	Gulshan
197	2019-09-25 12:53:06.668066	1	Pabna Sadar
198	2019-09-25 12:53:06.668066	1	Mohajan
199	2019-09-25 12:53:06.668066	1	Tushbhandar
200	2019-09-25 12:53:06.668066	1	Jhinaigati
201	2019-09-25 12:53:06.668066	1	Phultala
202	2019-09-25 12:53:06.668066	1	Sathia
203	2019-09-25 12:53:06.668066	1	Nilphamari Sadar
204	2019-09-25 12:53:06.668066	1	Moulvibazar Sadar
205	2019-09-25 12:53:06.668066	1	Patuakhali Sadar
206	2019-09-25 12:53:06.668066	1	Hatibandha
207	2019-09-25 12:53:06.668066	1	Doulatganj
208	2019-09-25 12:53:06.668066	1	Bheramara
209	2019-09-25 12:53:06.668066	1	Pirojpur Sadar
210	2019-09-25 12:53:06.668066	1	Baiddya Jam Toil
211	2019-09-25 12:53:06.668066	1	Kalia
212	2019-09-25 12:53:06.668066	1	Shibloya
213	2019-09-25 12:53:06.668066	1	Jhalokati Sadar
214	2019-09-25 12:53:06.668066	1	Nalitabari
215	2019-09-25 12:53:06.668066	1	Laxmipasha
216	2019-09-25 12:53:06.668066	1	Bhandaria
217	2019-09-25 12:53:06.668066	1	Jhikargachha
218	2019-09-25 12:53:06.668066	1	Morelganj
219	2019-09-25 12:53:06.668066	1	Bagha
220	2019-09-25 12:53:06.668066	1	Sreenagar
221	2019-09-25 12:53:06.668066	1	Nandigram
222	2019-09-25 12:53:06.668066	1	Motijheel
223	2019-09-25 12:53:06.668066	1	Aditmari
224	2019-09-25 12:53:06.668066	1	Nandail
225	2019-09-25 12:53:06.668066	1	Monirampur
226	2019-09-25 12:53:06.668066	1	Kazipur
227	2019-09-25 12:53:06.668066	1	Chotto Dab
228	2019-09-25 12:53:06.668066	1	Balaganj
229	2019-09-25 12:53:06.668066	1	Karimganj
230	2019-09-25 12:53:06.668066	1	Laxman
231	2019-09-25 12:53:06.668066	1	Kalauk
232	2019-09-25 12:53:06.668066	1	Sutrapur
233	2019-09-25 12:53:06.668066	1	Purbadhola
234	2019-09-25 12:53:06.668066	1	Isshwargonj
235	2019-09-25 12:53:06.668066	1	Kulaura
236	2019-09-25 12:53:06.668066	1	Haluaghat
237	2019-09-25 12:53:06.668066	1	Madukhali
238	2019-09-25 12:53:06.668066	1	Khepupara
239	2019-09-25 12:53:06.668066	1	Jatrabari
240	2019-09-25 12:53:06.668066	1	Babuganj
241	2019-09-25 12:53:06.668066	1	Chunarughat
242	2019-09-25 12:53:06.668066	1	Gajaria
243	2019-09-25 12:53:06.668066	1	Rajibpur
244	2019-09-25 12:53:06.668066	1	Lalmohan UPO
245	2019-09-25 12:53:06.668066	1	Gazipur Sadar
246	2019-09-25 12:53:06.668066	1	Kushtia Sadar
247	2019-09-25 12:53:06.668066	1	Sujanagar
248	2019-09-25 12:53:06.668066	1	Galachipa
249	2019-09-25 12:53:06.668066	1	Mirzapur
250	2019-09-25 12:53:06.668066	1	Patharghata
251	2019-09-25 12:53:06.668066	1	Mymensingh Sadar
252	2019-09-25 12:53:06.668066	1	Meherpur Sadar
253	2019-09-25 12:53:06.668066	1	Maheshpur
254	2019-09-25 12:53:06.668066	1	Bonarpara
255	2019-09-25 12:53:06.668066	1	Sunamganj Sadar
256	2019-09-25 12:53:06.668066	1	Shariatpur Sadar
257	2019-09-25 12:53:06.668066	1	Fatullah
258	2019-09-25 12:53:06.668066	1	Chirirbandar
259	2019-09-25 12:53:06.668066	1	Shribardi
260	2019-09-25 12:53:06.668066	1	Gouripur
261	2019-09-25 12:53:06.668066	1	Bamna
262	2019-09-25 12:53:06.668066	1	Jibanpur
263	2019-09-25 12:53:06.668066	1	Kuliarchar
264	2019-09-25 12:53:06.668066	1	Tanor
265	2019-09-25 12:53:06.668066	1	Jakiganj
266	2019-09-25 12:53:06.668066	1	Shailakupa
267	2019-09-25 12:53:06.668066	1	Batiaghat
268	2019-09-25 12:53:06.668066	1	Goainhat
269	2019-09-25 12:53:06.668066	1	Madarganj
270	2019-09-25 12:53:06.668066	1	Savar
271	2019-09-25 12:53:06.668066	1	Tangibari
272	2019-09-25 12:53:06.668066	1	Chalna Ankorage
273	2019-09-25 12:53:06.668066	1	Tarash
274	2019-09-25 12:53:06.668066	1	Tetulia
275	2019-09-25 12:53:06.668066	1	Ullapara
276	2019-09-25 12:53:06.668066	1	Bishwanath
277	2019-09-25 12:53:06.668066	1	Madaripur Sadar
278	2019-09-25 12:53:06.668066	1	Lechhraganj
279	2019-09-25 12:53:06.668066	1	Sripur
280	2019-09-25 12:53:06.668066	1	Ahsanganj
281	2019-09-25 12:53:06.668066	1	Jaintapur
282	2019-09-25 12:53:06.668066	1	Swarupkathi
283	2019-09-25 12:53:06.668066	1	Harua
284	2019-09-25 12:53:06.668066	1	Sherpur Shadar
285	2019-09-25 12:53:06.668066	1	Janipur
286	2019-09-25 12:53:06.668066	1	kaukhali
287	2019-09-25 12:53:06.668066	1	Dhunat
288	2019-09-25 12:53:06.668066	1	Shorishabari
289	2019-09-25 12:53:06.668066	1	Alamdanga
290	2019-09-25 12:53:06.668066	1	Madan
291	2019-09-25 12:53:06.668066	1	Setabganj
292	2019-09-25 12:53:06.668066	1	Sabujbag
293	2019-09-25 12:53:06.668066	1	Shibganj U.P.O
294	2019-09-25 12:53:06.668066	1	Keraniganj
295	2019-09-25 12:53:06.668066	1	Paikgachha
296	2019-09-25 12:53:06.668066	1	Belkuchi
297	2019-09-25 12:53:06.668066	1	Digalia
298	2019-09-25 12:53:06.668066	1	Keshabpur
299	2019-09-25 12:53:06.668066	1	Sakhipur
300	2019-09-25 12:53:06.668066	1	Netrakona Sadar
301	2019-09-25 12:53:06.668066	1	Jagnnathpur
302	2019-09-25 12:53:06.668066	1	Saturia
303	2019-09-25 12:53:06.668066	1	Duara bazar
304	2019-09-25 12:53:06.668066	1	Palash
305	2019-09-25 12:53:06.668066	1	Baliakandi
306	2019-09-25 12:53:06.668066	1	Dewangonj
307	2019-09-25 12:53:06.668066	1	Katiadi
308	2019-09-25 12:53:06.668066	1	Fakirhat
309	2019-09-25 12:53:06.668066	1	Kahalu
310	2019-09-25 12:53:06.668066	1	Thakurgaon Sadar
311	2019-09-25 12:53:06.668066	1	panchbibi
312	2019-09-25 12:53:06.668066	1	Charbhadrasan
313	2019-09-25 12:53:06.668066	1	Chaugachha
314	2019-09-25 12:53:06.668066	1	Lalbag
315	2019-09-25 12:53:06.668066	1	Palton
316	2019-09-25 12:53:06.668066	1	Shripur
317	2019-09-25 12:53:06.668066	1	Chhatak
318	2019-09-25 12:53:06.668066	1	Tungipara
319	2019-09-25 12:53:06.668066	1	Naria
320	2019-09-25 12:53:06.668066	1	Khilkhet
321	2019-09-25 12:53:06.668066	1	Aftabnagar
322	2019-09-25 12:53:06.668066	1	Nalchhiti
323	2019-09-25 12:53:06.668066	1	Dhobaura
324	2019-09-25 12:53:06.668066	1	Gangachara
325	2019-09-25 12:53:06.668066	1	Madhupur
326	2019-09-25 12:53:06.668066	1	Saidpur
327	2019-09-25 12:53:06.668066	1	Khod Mohanpur
328	2019-09-25 12:53:06.668066	1	Jhenaidah Sadar
329	2019-09-25 12:53:06.668066	1	Muladi
330	2019-09-25 12:53:06.668066	1	Sherpur
331	2019-09-25 12:53:06.668066	1	Panchagra Sadar
332	2019-09-25 12:53:06.668066	1	Parbatipur
333	2019-09-25 12:53:06.668066	1	Raninagar
334	2019-09-25 12:53:06.668066	1	Kotalipara
335	2019-09-25 12:53:06.668066	1	Godagari
336	2019-09-25 12:53:06.668066	1	Borhanuddin UPO
337	2019-09-25 12:53:06.668066	1	Domar
338	2019-09-25 12:53:06.668066	1	Sachna
339	2019-09-25 12:53:06.668066	1	Chatmohar
340	2019-09-25 12:53:06.668066	1	Bishamsarpur
341	2019-09-25 12:53:06.668066	1	Baralekha
342	2019-09-25 12:53:06.668066	1	Hossenpur
343	2019-09-25 12:53:06.668066	1	Kamalganj
344	2019-09-25 12:53:06.668066	1	Charfashion
345	2019-09-25 12:53:06.668066	1	Harinakundu
346	2019-09-25 12:53:06.668066	1	Barguna Sadar
347	2019-09-25 12:53:06.668066	1	Roumari
348	2019-09-25 12:53:06.668066	1	Hajirhat
349	2019-09-25 12:53:06.668066	1	Bahubal
350	2019-09-25 12:53:06.668066	1	Alaipur
351	2019-09-25 12:53:06.668066	1	Shibganj
352	2019-09-25 12:53:06.668066	1	Kaliakaar
353	2019-09-25 12:53:06.668066	1	Lalmonirhat Sadar
354	2019-09-25 12:53:06.668066	1	Koyra
355	2019-09-25 12:53:06.668066	1	Damudhya
356	2019-09-25 12:53:06.668066	1	Alfadanga
357	2019-09-25 12:53:06.668066	1	Jashore Sadar
358	2019-09-25 12:53:06.668066	1	Delduar
359	2019-09-25 12:53:06.668066	1	Shahjadpur
360	2019-09-25 12:53:06.668066	1	Rajshahi Sadar
361	2019-09-25 12:53:06.668066	1	Bakerganj
362	2019-09-25 12:53:06.668066	1	Barhatta
363	2019-09-25 12:53:06.668066	1	Gaforgaon
364	2019-09-25 12:53:06.668066	1	Mohammadpur
365	2019-09-25 12:53:06.668066	1	Dhamrai
366	2019-09-25 12:53:06.668066	1	Badarganj
367	2019-09-25 12:53:06.668066	1	Mithapukur
368	2019-09-25 12:53:06.668066	1	Tejgaon Industrial Area
369	2019-09-25 12:53:06.668066	1	Boda
370	2019-09-25 12:53:06.668066	1	Ghungiar
371	2019-09-25 12:53:06.668066	1	Lohajong
372	2019-09-25 12:53:06.668066	1	Taraganj
373	2019-09-25 12:53:06.668066	1	Sylhet Sadar
374	2019-09-25 12:53:06.668066	1	Khaliajuri
375	2019-09-25 12:53:06.668066	1	Sreepur
376	2019-09-25 12:53:06.668066	1	Kishoriganj
377	2019-09-25 12:53:06.668066	1	Mathbaria
378	2019-09-25 12:53:06.668066	1	Phulchhari
379	2019-09-25 12:53:06.668066	1	Subidkhali
380	2019-09-25 12:53:06.668066	1	Natore Sadar
381	2019-09-25 12:53:06.668066	1	Bauphal
382	2019-09-25 12:53:06.668066	1	Debottar
383	2019-09-25 12:53:06.668066	1	Palashbari
384	2019-09-25 12:53:06.668066	1	Mollahat
385	2019-09-25 12:53:06.668066	1	Sadarpur
386	2019-09-25 12:53:06.668066	1	Terakhada
387	2019-09-25 12:53:06.668066	1	Nachol
388	2019-09-25 12:53:06.668066	1	Moddhynagar
389	2019-09-25 12:53:06.668066	1	New market
390	2019-09-25 12:53:06.668066	1	Kompanyganj
391	2019-09-25 12:53:06.668066	1	Joypara
392	2019-09-25 12:53:06.668066	1	Betagi
393	2019-09-25 12:53:06.668066	1	Narayanganj Sadar
394	2019-09-25 12:53:06.668066	1	Nagarkanda
395	2019-09-25 12:53:06.668066	1	Jaldhaka
396	2019-09-25 12:53:06.668066	1	Nakipur
397	2019-09-25 12:53:06.668066	1	Jamalpur
\.


--
-- TOC entry 3148 (class 2606 OID 16630)
-- Name: thana thana_pkey; Type: CONSTRAINT; Schema: public; Owner: shikhargupta
--

ALTER TABLE ONLY public.thana
    ADD CONSTRAINT thana_pkey PRIMARY KEY (id);


-- Completed on 2019-09-25 13:07:16 IST

--
-- PostgreSQL database dump complete
--
