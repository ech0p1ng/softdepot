--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.1

-- Started on 2024-05-21 00:27:18

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'WIN1251';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 42606)
-- Name: administrator; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.administrator (
    id integer NOT NULL,
    email character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    administrator_name character varying(50) NOT NULL
);


ALTER TABLE public.administrator OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 42605)
-- Name: administrator_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.administrator_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.administrator_id_seq OWNER TO postgres;

--
-- TOC entry 4890 (class 0 OID 0)
-- Dependencies: 215
-- Name: administrator_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.administrator_id_seq OWNED BY public.administrator.id;


--
-- TOC entry 218 (class 1259 OID 42615)
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    id integer NOT NULL,
    customer_name character varying(50) NOT NULL,
    email character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    profile_img_url character varying(100),
    balance numeric(10,2),
    CONSTRAINT customer_balance_check CHECK ((balance >= (0)::numeric))
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 42614)
-- Name: customer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.customer_id_seq OWNER TO postgres;

--
-- TOC entry 4892 (class 0 OID 0)
-- Dependencies: 217
-- Name: customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customer_id_seq OWNED BY public.customer.id;


--
-- TOC entry 230 (class 1259 OID 42693)
-- Name: daily_stats; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.daily_stats (
    id integer NOT NULL,
    stats_date timestamp without time zone NOT NULL,
    program_id integer NOT NULL,
    avg_estimation double precision NOT NULL,
    earnings numeric(10,2),
    purchases_amount integer NOT NULL,
    reviews_amount integer NOT NULL,
    CONSTRAINT daily_stats_avg_estimation_check CHECK (((avg_estimation >= (0)::double precision) AND (avg_estimation <= (5)::double precision))),
    CONSTRAINT daily_stats_earnings_check CHECK ((earnings >= (0)::numeric))
);


ALTER TABLE public.daily_stats OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 42692)
-- Name: daily_stats_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.daily_stats_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.daily_stats_id_seq OWNER TO postgres;

--
-- TOC entry 4895 (class 0 OID 0)
-- Dependencies: 229
-- Name: daily_stats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.daily_stats_id_seq OWNED BY public.daily_stats.id;


--
-- TOC entry 226 (class 1259 OID 42658)
-- Name: degree_of_belonging; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.degree_of_belonging (
    id integer NOT NULL,
    program_id integer NOT NULL,
    tag_id integer NOT NULL,
    degree_value integer NOT NULL,
    CONSTRAINT degree_of_belonging_degree_value_check CHECK (((degree_value >= 0) AND (degree_value <= 10)))
);


ALTER TABLE public.degree_of_belonging OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 42657)
-- Name: degree_of_belonging_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.degree_of_belonging_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.degree_of_belonging_id_seq OWNER TO postgres;

--
-- TOC entry 4897 (class 0 OID 0)
-- Dependencies: 225
-- Name: degree_of_belonging_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.degree_of_belonging_id_seq OWNED BY public.degree_of_belonging.id;


--
-- TOC entry 220 (class 1259 OID 42625)
-- Name: developer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.developer (
    id integer NOT NULL,
    email character varying(50) NOT NULL,
    developer_name character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    profile_img_url character varying(100)
);


ALTER TABLE public.developer OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 42624)
-- Name: developer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.developer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.developer_id_seq OWNER TO postgres;

--
-- TOC entry 4899 (class 0 OID 0)
-- Dependencies: 219
-- Name: developer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.developer_id_seq OWNED BY public.developer.id;


--
-- TOC entry 224 (class 1259 OID 42643)
-- Name: program; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.program (
    id integer NOT NULL,
    program_name character varying(50) NOT NULL,
    price numeric(10,2),
    description character varying(50) NOT NULL,
    logo_url character varying(100) NOT NULL,
    installer_windows_url character varying(100),
    installer_linux_url character varying(100),
    installer_macos_url character varying(100),
    screenshots_url character varying(100)[],
    developer_id integer NOT NULL,
    CONSTRAINT program_price_check CHECK ((price >= (0)::numeric))
);


ALTER TABLE public.program OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 42642)
-- Name: program_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.program_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.program_id_seq OWNER TO postgres;

--
-- TOC entry 4902 (class 0 OID 0)
-- Dependencies: 223
-- Name: program_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.program_id_seq OWNED BY public.program.id;


--
-- TOC entry 228 (class 1259 OID 42676)
-- Name: purchase; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.purchase (
    id integer NOT NULL,
    purchase_date_time timestamp without time zone NOT NULL,
    customer_id integer NOT NULL,
    program_id integer NOT NULL
);


ALTER TABLE public.purchase OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 42675)
-- Name: purchase_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.purchase_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.purchase_id_seq OWNER TO postgres;

--
-- TOC entry 4904 (class 0 OID 0)
-- Dependencies: 227
-- Name: purchase_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.purchase_id_seq OWNED BY public.purchase.id;


--
-- TOC entry 232 (class 1259 OID 42707)
-- Name: review; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.review (
    id integer NOT NULL,
    customer_id integer NOT NULL,
    program_id integer NOT NULL,
    estimation integer NOT NULL,
    date_time timestamp without time zone NOT NULL,
    review_text text NOT NULL,
    CONSTRAINT review_estimation_check CHECK (((estimation >= 0) AND (estimation <= 5)))
);


ALTER TABLE public.review OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 42706)
-- Name: review_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.review_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.review_id_seq OWNER TO postgres;

--
-- TOC entry 4906 (class 0 OID 0)
-- Dependencies: 231
-- Name: review_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.review_id_seq OWNED BY public.review.id;


--
-- TOC entry 222 (class 1259 OID 42634)
-- Name: tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tag (
    id integer NOT NULL,
    tag_name character varying(50) NOT NULL
);


ALTER TABLE public.tag OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 42633)
-- Name: tag_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tag_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tag_id_seq OWNER TO postgres;

--
-- TOC entry 4907 (class 0 OID 0)
-- Dependencies: 221
-- Name: tag_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tag_id_seq OWNED BY public.tag.id;


--
-- TOC entry 4674 (class 2604 OID 42609)
-- Name: administrator id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrator ALTER COLUMN id SET DEFAULT nextval('public.administrator_id_seq'::regclass);


--
-- TOC entry 4675 (class 2604 OID 42618)
-- Name: customer id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer ALTER COLUMN id SET DEFAULT nextval('public.customer_id_seq'::regclass);


--
-- TOC entry 4681 (class 2604 OID 42696)
-- Name: daily_stats id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.daily_stats ALTER COLUMN id SET DEFAULT nextval('public.daily_stats_id_seq'::regclass);


--
-- TOC entry 4679 (class 2604 OID 42661)
-- Name: degree_of_belonging id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.degree_of_belonging ALTER COLUMN id SET DEFAULT nextval('public.degree_of_belonging_id_seq'::regclass);


--
-- TOC entry 4676 (class 2604 OID 42628)
-- Name: developer id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.developer ALTER COLUMN id SET DEFAULT nextval('public.developer_id_seq'::regclass);


--
-- TOC entry 4678 (class 2604 OID 42646)
-- Name: program id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.program ALTER COLUMN id SET DEFAULT nextval('public.program_id_seq'::regclass);


--
-- TOC entry 4680 (class 2604 OID 42679)
-- Name: purchase id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase ALTER COLUMN id SET DEFAULT nextval('public.purchase_id_seq'::regclass);


--
-- TOC entry 4682 (class 2604 OID 42710)
-- Name: review id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.review ALTER COLUMN id SET DEFAULT nextval('public.review_id_seq'::regclass);


--
-- TOC entry 4677 (class 2604 OID 42637)
-- Name: tag id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tag ALTER COLUMN id SET DEFAULT nextval('public.tag_id_seq'::regclass);


--
-- TOC entry 4867 (class 0 OID 42606)
-- Dependencies: 216
-- Data for Name: administrator; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.administrator (id, email, password, administrator_name) FROM stdin;
1	alexey2003petrov@yandex.ru	adminadmin	alexey2003petrov
\.


--
-- TOC entry 4869 (class 0 OID 42615)
-- Dependencies: 218
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customer (id, customer_name, email, password, profile_img_url, balance) FROM stdin;
1	ech0p1ng	alex5.56@yandex.ru	customercustomer123	\N	22600.00
\.


--
-- TOC entry 4881 (class 0 OID 42693)
-- Dependencies: 230
-- Data for Name: daily_stats; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.daily_stats (id, stats_date, program_id, avg_estimation, earnings, purchases_amount, reviews_amount) FROM stdin;
\.


--
-- TOC entry 4877 (class 0 OID 42658)
-- Dependencies: 226
-- Data for Name: degree_of_belonging; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.degree_of_belonging (id, program_id, tag_id, degree_value) FROM stdin;
3	2	2	2
4	2	1	3
\.


--
-- TOC entry 4871 (class 0 OID 42625)
-- Dependencies: 220
-- Data for Name: developer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.developer (id, email, developer_name, password, profile_img_url) FROM stdin;
1	lesha2003petrov@gmail.com	SoftDepotDEV	developerdeveloper	
\.


--
-- TOC entry 4875 (class 0 OID 42643)
-- Dependencies: 224
-- Data for Name: program; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.program (id, program_name, price, description, logo_url, installer_windows_url, installer_linux_url, installer_macos_url, screenshots_url, developer_id) FROM stdin;
2	Test program name	1200.00	Test program description					{}	1
\.


--
-- TOC entry 4879 (class 0 OID 42676)
-- Dependencies: 228
-- Data for Name: purchase; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.purchase (id, purchase_date_time, customer_id, program_id) FROM stdin;
3	2024-05-19 19:45:31.749772	1	2
\.


--
-- TOC entry 4883 (class 0 OID 42707)
-- Dependencies: 232
-- Data for Name: review; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.review (id, customer_id, program_id, estimation, date_time, review_text) FROM stdin;
3	1	2	5	2024-05-19 19:47:15.489259	Test review for program with id=2
\.


--
-- TOC entry 4873 (class 0 OID 42634)
-- Dependencies: 222
-- Data for Name: tag; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tag (id, tag_name) FROM stdin;
1	Гонки
2	От третьего лица
3	Шутер
4	От первого лица
\.


--
-- TOC entry 4908 (class 0 OID 0)
-- Dependencies: 215
-- Name: administrator_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.administrator_id_seq', 1, true);


--
-- TOC entry 4909 (class 0 OID 0)
-- Dependencies: 217
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customer_id_seq', 1, true);


--
-- TOC entry 4910 (class 0 OID 0)
-- Dependencies: 229
-- Name: daily_stats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.daily_stats_id_seq', 1, false);


--
-- TOC entry 4911 (class 0 OID 0)
-- Dependencies: 225
-- Name: degree_of_belonging_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.degree_of_belonging_id_seq', 4, true);


--
-- TOC entry 4912 (class 0 OID 0)
-- Dependencies: 219
-- Name: developer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.developer_id_seq', 1, true);


--
-- TOC entry 4913 (class 0 OID 0)
-- Dependencies: 223
-- Name: program_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.program_id_seq', 2, true);


--
-- TOC entry 4914 (class 0 OID 0)
-- Dependencies: 227
-- Name: purchase_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.purchase_id_seq', 3, true);


--
-- TOC entry 4915 (class 0 OID 0)
-- Dependencies: 231
-- Name: review_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.review_id_seq', 3, true);


--
-- TOC entry 4916 (class 0 OID 0)
-- Dependencies: 221
-- Name: tag_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tag_id_seq', 4, true);


--
-- TOC entry 4690 (class 2606 OID 42613)
-- Name: administrator administrator_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrator
    ADD CONSTRAINT administrator_email_key UNIQUE (email);


--
-- TOC entry 4692 (class 2606 OID 42611)
-- Name: administrator administrator_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrator
    ADD CONSTRAINT administrator_pkey PRIMARY KEY (id);


--
-- TOC entry 4694 (class 2606 OID 42623)
-- Name: customer customer_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_email_key UNIQUE (email);


--
-- TOC entry 4696 (class 2606 OID 42621)
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- TOC entry 4712 (class 2606 OID 42700)
-- Name: daily_stats daily_stats_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.daily_stats
    ADD CONSTRAINT daily_stats_pkey PRIMARY KEY (id);


--
-- TOC entry 4708 (class 2606 OID 42664)
-- Name: degree_of_belonging degree_of_belonging_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.degree_of_belonging
    ADD CONSTRAINT degree_of_belonging_pkey PRIMARY KEY (id);


--
-- TOC entry 4698 (class 2606 OID 42632)
-- Name: developer developer_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.developer
    ADD CONSTRAINT developer_email_key UNIQUE (email);


--
-- TOC entry 4700 (class 2606 OID 42630)
-- Name: developer developer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.developer
    ADD CONSTRAINT developer_pkey PRIMARY KEY (id);


--
-- TOC entry 4706 (class 2606 OID 42651)
-- Name: program program_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.program
    ADD CONSTRAINT program_pkey PRIMARY KEY (id);


--
-- TOC entry 4710 (class 2606 OID 42681)
-- Name: purchase purchase_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase
    ADD CONSTRAINT purchase_pkey PRIMARY KEY (id);


--
-- TOC entry 4714 (class 2606 OID 42715)
-- Name: review review_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.review
    ADD CONSTRAINT review_pkey PRIMARY KEY (id);


--
-- TOC entry 4702 (class 2606 OID 42639)
-- Name: tag tag_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);


--
-- TOC entry 4704 (class 2606 OID 42641)
-- Name: tag tag_tag_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tag
    ADD CONSTRAINT tag_tag_name_key UNIQUE (tag_name);


--
-- TOC entry 4720 (class 2606 OID 42701)
-- Name: daily_stats daily_stats_program_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.daily_stats
    ADD CONSTRAINT daily_stats_program_id_fkey FOREIGN KEY (program_id) REFERENCES public.program(id) ON DELETE CASCADE;


--
-- TOC entry 4716 (class 2606 OID 42665)
-- Name: degree_of_belonging degree_of_belonging_program_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.degree_of_belonging
    ADD CONSTRAINT degree_of_belonging_program_id_fkey FOREIGN KEY (program_id) REFERENCES public.program(id) ON DELETE CASCADE;


--
-- TOC entry 4717 (class 2606 OID 42670)
-- Name: degree_of_belonging degree_of_belonging_tag_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.degree_of_belonging
    ADD CONSTRAINT degree_of_belonging_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES public.tag(id) ON DELETE CASCADE;


--
-- TOC entry 4715 (class 2606 OID 42652)
-- Name: program program_developer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.program
    ADD CONSTRAINT program_developer_id_fkey FOREIGN KEY (developer_id) REFERENCES public.developer(id);


--
-- TOC entry 4718 (class 2606 OID 42682)
-- Name: purchase purchase_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase
    ADD CONSTRAINT purchase_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customer(id) ON DELETE CASCADE;


--
-- TOC entry 4719 (class 2606 OID 42687)
-- Name: purchase purchase_program_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase
    ADD CONSTRAINT purchase_program_id_fkey FOREIGN KEY (program_id) REFERENCES public.program(id) ON DELETE CASCADE;


--
-- TOC entry 4721 (class 2606 OID 42716)
-- Name: review review_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.review
    ADD CONSTRAINT review_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customer(id) ON DELETE CASCADE;


--
-- TOC entry 4722 (class 2606 OID 42721)
-- Name: review review_program_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.review
    ADD CONSTRAINT review_program_id_fkey FOREIGN KEY (program_id) REFERENCES public.program(id) ON DELETE CASCADE;


--
-- TOC entry 4889 (class 0 OID 0)
-- Dependencies: 216
-- Name: TABLE administrator; Type: ACL; Schema: public; Owner: postgres
--

GRANT SELECT ON TABLE public.administrator TO customer_role;
GRANT SELECT ON TABLE public.administrator TO developer_role;
GRANT SELECT ON TABLE public.administrator TO unregistered_role;


--
-- TOC entry 4891 (class 0 OID 0)
-- Dependencies: 218
-- Name: TABLE customer; Type: ACL; Schema: public; Owner: postgres
--

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.customer TO customer_role;
GRANT SELECT ON TABLE public.customer TO developer_role;
GRANT SELECT ON TABLE public.customer TO unregistered_role;


--
-- TOC entry 4893 (class 0 OID 0)
-- Dependencies: 217
-- Name: SEQUENCE customer_id_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT SELECT,USAGE ON SEQUENCE public.customer_id_seq TO customer_role;


--
-- TOC entry 4894 (class 0 OID 0)
-- Dependencies: 230
-- Name: TABLE daily_stats; Type: ACL; Schema: public; Owner: postgres
--

GRANT SELECT ON TABLE public.daily_stats TO developer_role;


--
-- TOC entry 4896 (class 0 OID 0)
-- Dependencies: 226
-- Name: TABLE degree_of_belonging; Type: ACL; Schema: public; Owner: postgres
--

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.degree_of_belonging TO developer_role;


--
-- TOC entry 4898 (class 0 OID 0)
-- Dependencies: 220
-- Name: TABLE developer; Type: ACL; Schema: public; Owner: postgres
--

GRANT SELECT ON TABLE public.developer TO customer_role;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.developer TO developer_role;
GRANT SELECT ON TABLE public.developer TO unregistered_role;


--
-- TOC entry 4900 (class 0 OID 0)
-- Dependencies: 219
-- Name: SEQUENCE developer_id_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT SELECT,USAGE ON SEQUENCE public.developer_id_seq TO developer_role;


--
-- TOC entry 4901 (class 0 OID 0)
-- Dependencies: 224
-- Name: TABLE program; Type: ACL; Schema: public; Owner: postgres
--

GRANT SELECT ON TABLE public.program TO customer_role;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.program TO developer_role;
GRANT SELECT ON TABLE public.program TO unregistered_role;


--
-- TOC entry 4903 (class 0 OID 0)
-- Dependencies: 228
-- Name: TABLE purchase; Type: ACL; Schema: public; Owner: postgres
--

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.purchase TO customer_role;


--
-- TOC entry 4905 (class 0 OID 0)
-- Dependencies: 232
-- Name: TABLE review; Type: ACL; Schema: public; Owner: postgres
--

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.review TO customer_role;
GRANT SELECT ON TABLE public.review TO developer_role;
GRANT SELECT ON TABLE public.review TO unregistered_role;


-- Completed on 2024-05-21 00:27:18

--
-- PostgreSQL database dump complete
--

