PGDMP                       |         	   softdepot    16.1    16.1 Y               0    0    ENCODING    ENCODING     !   SET client_encoding = 'WIN1251';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    42604 	   softdepot    DATABASE     }   CREATE DATABASE softdepot WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE softdepot;
                postgres    false            �            1259    42606    administrator    TABLE     �   CREATE TABLE public.administrator (
    id integer NOT NULL,
    email character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    administrator_name character varying(50) NOT NULL
);
 !   DROP TABLE public.administrator;
       public         heap    postgres    false                       0    0    TABLE administrator    ACL     �   GRANT SELECT ON TABLE public.administrator TO customer_role;
GRANT SELECT ON TABLE public.administrator TO developer_role;
GRANT SELECT ON TABLE public.administrator TO unregistered_role;
          public          postgres    false    216            �            1259    42605    administrator_id_seq    SEQUENCE     �   CREATE SEQUENCE public.administrator_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.administrator_id_seq;
       public          postgres    false    216                       0    0    administrator_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.administrator_id_seq OWNED BY public.administrator.id;
          public          postgres    false    215            �            1259    42615    customer    TABLE     S  CREATE TABLE public.customer (
    id integer NOT NULL,
    customer_name character varying(50) NOT NULL,
    email character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    profile_img_url character varying(100),
    balance numeric(10,2),
    CONSTRAINT customer_balance_check CHECK ((balance >= (0)::numeric))
);
    DROP TABLE public.customer;
       public         heap    postgres    false                       0    0    TABLE customer    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.customer TO customer_role;
GRANT SELECT ON TABLE public.customer TO developer_role;
GRANT SELECT ON TABLE public.customer TO unregistered_role;
          public          postgres    false    218            �            1259    42614    customer_id_seq    SEQUENCE     �   CREATE SEQUENCE public.customer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.customer_id_seq;
       public          postgres    false    218                       0    0    customer_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.customer_id_seq OWNED BY public.customer.id;
          public          postgres    false    217                       0    0    SEQUENCE customer_id_seq    ACL     H   GRANT SELECT,USAGE ON SEQUENCE public.customer_id_seq TO customer_role;
          public          postgres    false    217            �            1259    42693    daily_stats    TABLE     
  CREATE TABLE public.daily_stats (
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
    DROP TABLE public.daily_stats;
       public         heap    postgres    false                       0    0    TABLE daily_stats    ACL     <   GRANT SELECT ON TABLE public.daily_stats TO developer_role;
          public          postgres    false    230            �            1259    42692    daily_stats_id_seq    SEQUENCE     �   CREATE SEQUENCE public.daily_stats_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.daily_stats_id_seq;
       public          postgres    false    230                        0    0    daily_stats_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.daily_stats_id_seq OWNED BY public.daily_stats.id;
          public          postgres    false    229            �            1259    42658    degree_of_belonging    TABLE       CREATE TABLE public.degree_of_belonging (
    id integer NOT NULL,
    program_id integer NOT NULL,
    tag_id integer NOT NULL,
    degree_value integer NOT NULL,
    CONSTRAINT degree_of_belonging_degree_value_check CHECK (((degree_value >= 0) AND (degree_value <= 10)))
);
 '   DROP TABLE public.degree_of_belonging;
       public         heap    postgres    false            !           0    0    TABLE degree_of_belonging    ACL     Y   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.degree_of_belonging TO developer_role;
          public          postgres    false    226            �            1259    42657    degree_of_belonging_id_seq    SEQUENCE     �   CREATE SEQUENCE public.degree_of_belonging_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.degree_of_belonging_id_seq;
       public          postgres    false    226            "           0    0    degree_of_belonging_id_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.degree_of_belonging_id_seq OWNED BY public.degree_of_belonging.id;
          public          postgres    false    225            �            1259    42625 	   developer    TABLE     �   CREATE TABLE public.developer (
    id integer NOT NULL,
    email character varying(50) NOT NULL,
    developer_name character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    profile_img_url character varying(100)
);
    DROP TABLE public.developer;
       public         heap    postgres    false            #           0    0    TABLE developer    ACL     �   GRANT SELECT ON TABLE public.developer TO customer_role;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.developer TO developer_role;
GRANT SELECT ON TABLE public.developer TO unregistered_role;
          public          postgres    false    220            �            1259    42624    developer_id_seq    SEQUENCE     �   CREATE SEQUENCE public.developer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.developer_id_seq;
       public          postgres    false    220            $           0    0    developer_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.developer_id_seq OWNED BY public.developer.id;
          public          postgres    false    219            %           0    0    SEQUENCE developer_id_seq    ACL     J   GRANT SELECT,USAGE ON SEQUENCE public.developer_id_seq TO developer_role;
          public          postgres    false    219            �            1259    42643    program    TABLE       CREATE TABLE public.program (
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
    DROP TABLE public.program;
       public         heap    postgres    false            &           0    0    TABLE program    ACL     �   GRANT SELECT ON TABLE public.program TO customer_role;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.program TO developer_role;
GRANT SELECT ON TABLE public.program TO unregistered_role;
          public          postgres    false    224            �            1259    42642    program_id_seq    SEQUENCE     �   CREATE SEQUENCE public.program_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.program_id_seq;
       public          postgres    false    224            '           0    0    program_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.program_id_seq OWNED BY public.program.id;
          public          postgres    false    223            �            1259    42676    purchase    TABLE     �   CREATE TABLE public.purchase (
    id integer NOT NULL,
    purchase_date_time timestamp without time zone NOT NULL,
    customer_id integer NOT NULL,
    program_id integer NOT NULL
);
    DROP TABLE public.purchase;
       public         heap    postgres    false            (           0    0    TABLE purchase    ACL     M   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.purchase TO customer_role;
          public          postgres    false    228            �            1259    42675    purchase_id_seq    SEQUENCE     �   CREATE SEQUENCE public.purchase_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.purchase_id_seq;
       public          postgres    false    228            )           0    0    purchase_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.purchase_id_seq OWNED BY public.purchase.id;
          public          postgres    false    227            �            1259    42707    review    TABLE     I  CREATE TABLE public.review (
    id integer NOT NULL,
    customer_id integer NOT NULL,
    program_id integer NOT NULL,
    estimation integer NOT NULL,
    date_time timestamp without time zone NOT NULL,
    review_text text NOT NULL,
    CONSTRAINT review_estimation_check CHECK (((estimation >= 0) AND (estimation <= 5)))
);
    DROP TABLE public.review;
       public         heap    postgres    false            *           0    0    TABLE review    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.review TO customer_role;
GRANT SELECT ON TABLE public.review TO developer_role;
GRANT SELECT ON TABLE public.review TO unregistered_role;
          public          postgres    false    232            �            1259    42706    review_id_seq    SEQUENCE     �   CREATE SEQUENCE public.review_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.review_id_seq;
       public          postgres    false    232            +           0    0    review_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.review_id_seq OWNED BY public.review.id;
          public          postgres    false    231            �            1259    42634    tag    TABLE     b   CREATE TABLE public.tag (
    id integer NOT NULL,
    tag_name character varying(50) NOT NULL
);
    DROP TABLE public.tag;
       public         heap    postgres    false            �            1259    42633 
   tag_id_seq    SEQUENCE     �   CREATE SEQUENCE public.tag_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.tag_id_seq;
       public          postgres    false    222            ,           0    0 
   tag_id_seq    SEQUENCE OWNED BY     9   ALTER SEQUENCE public.tag_id_seq OWNED BY public.tag.id;
          public          postgres    false    221            B           2604    42609    administrator id    DEFAULT     t   ALTER TABLE ONLY public.administrator ALTER COLUMN id SET DEFAULT nextval('public.administrator_id_seq'::regclass);
 ?   ALTER TABLE public.administrator ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            C           2604    42618    customer id    DEFAULT     j   ALTER TABLE ONLY public.customer ALTER COLUMN id SET DEFAULT nextval('public.customer_id_seq'::regclass);
 :   ALTER TABLE public.customer ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    218    218            I           2604    42696    daily_stats id    DEFAULT     p   ALTER TABLE ONLY public.daily_stats ALTER COLUMN id SET DEFAULT nextval('public.daily_stats_id_seq'::regclass);
 =   ALTER TABLE public.daily_stats ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    230    229    230            G           2604    42661    degree_of_belonging id    DEFAULT     �   ALTER TABLE ONLY public.degree_of_belonging ALTER COLUMN id SET DEFAULT nextval('public.degree_of_belonging_id_seq'::regclass);
 E   ALTER TABLE public.degree_of_belonging ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    225    226    226            D           2604    42628    developer id    DEFAULT     l   ALTER TABLE ONLY public.developer ALTER COLUMN id SET DEFAULT nextval('public.developer_id_seq'::regclass);
 ;   ALTER TABLE public.developer ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    219    220    220            F           2604    42646 
   program id    DEFAULT     h   ALTER TABLE ONLY public.program ALTER COLUMN id SET DEFAULT nextval('public.program_id_seq'::regclass);
 9   ALTER TABLE public.program ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    223    224    224            H           2604    42679    purchase id    DEFAULT     j   ALTER TABLE ONLY public.purchase ALTER COLUMN id SET DEFAULT nextval('public.purchase_id_seq'::regclass);
 :   ALTER TABLE public.purchase ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    227    228    228            J           2604    42710 	   review id    DEFAULT     f   ALTER TABLE ONLY public.review ALTER COLUMN id SET DEFAULT nextval('public.review_id_seq'::regclass);
 8   ALTER TABLE public.review ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    231    232    232            E           2604    42637    tag id    DEFAULT     `   ALTER TABLE ONLY public.tag ALTER COLUMN id SET DEFAULT nextval('public.tag_id_seq'::regclass);
 5   ALTER TABLE public.tag ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    221    222    222                      0    42606    administrator 
   TABLE DATA           P   COPY public.administrator (id, email, password, administrator_name) FROM stdin;
    public          postgres    false    216   j                 0    42615    customer 
   TABLE DATA           `   COPY public.customer (id, customer_name, email, password, profile_img_url, balance) FROM stdin;
    public          postgres    false    218   `j                 0    42693    daily_stats 
   TABLE DATA           }   COPY public.daily_stats (id, stats_date, program_id, avg_estimation, earnings, purchases_amount, reviews_amount) FROM stdin;
    public          postgres    false    230   �j                 0    42658    degree_of_belonging 
   TABLE DATA           S   COPY public.degree_of_belonging (id, program_id, tag_id, degree_value) FROM stdin;
    public          postgres    false    226   �j                 0    42625 	   developer 
   TABLE DATA           Y   COPY public.developer (id, email, developer_name, password, profile_img_url) FROM stdin;
    public          postgres    false    220   �j                 0    42643    program 
   TABLE DATA           �   COPY public.program (id, program_name, price, description, logo_url, installer_windows_url, installer_linux_url, installer_macos_url, screenshots_url, developer_id) FROM stdin;
    public          postgres    false    224   Lk                 0    42676    purchase 
   TABLE DATA           S   COPY public.purchase (id, purchase_date_time, customer_id, program_id) FROM stdin;
    public          postgres    false    228   �k                 0    42707    review 
   TABLE DATA           a   COPY public.review (id, customer_id, program_id, estimation, date_time, review_text) FROM stdin;
    public          postgres    false    232   �k       	          0    42634    tag 
   TABLE DATA           +   COPY public.tag (id, tag_name) FROM stdin;
    public          postgres    false    222   8l       -           0    0    administrator_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.administrator_id_seq', 1, true);
          public          postgres    false    215            .           0    0    customer_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.customer_id_seq', 1, true);
          public          postgres    false    217            /           0    0    daily_stats_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.daily_stats_id_seq', 1, false);
          public          postgres    false    229            0           0    0    degree_of_belonging_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.degree_of_belonging_id_seq', 4, true);
          public          postgres    false    225            1           0    0    developer_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.developer_id_seq', 1, true);
          public          postgres    false    219            2           0    0    program_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.program_id_seq', 2, true);
          public          postgres    false    223            3           0    0    purchase_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.purchase_id_seq', 3, true);
          public          postgres    false    227            4           0    0    review_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.review_id_seq', 3, true);
          public          postgres    false    231            5           0    0 
   tag_id_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('public.tag_id_seq', 4, true);
          public          postgres    false    221            R           2606    42613 %   administrator administrator_email_key 
   CONSTRAINT     a   ALTER TABLE ONLY public.administrator
    ADD CONSTRAINT administrator_email_key UNIQUE (email);
 O   ALTER TABLE ONLY public.administrator DROP CONSTRAINT administrator_email_key;
       public            postgres    false    216            T           2606    42611     administrator administrator_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.administrator
    ADD CONSTRAINT administrator_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.administrator DROP CONSTRAINT administrator_pkey;
       public            postgres    false    216            V           2606    42623    customer customer_email_key 
   CONSTRAINT     W   ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_email_key UNIQUE (email);
 E   ALTER TABLE ONLY public.customer DROP CONSTRAINT customer_email_key;
       public            postgres    false    218            X           2606    42621    customer customer_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.customer DROP CONSTRAINT customer_pkey;
       public            postgres    false    218            h           2606    42700    daily_stats daily_stats_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.daily_stats
    ADD CONSTRAINT daily_stats_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.daily_stats DROP CONSTRAINT daily_stats_pkey;
       public            postgres    false    230            d           2606    42664 ,   degree_of_belonging degree_of_belonging_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.degree_of_belonging
    ADD CONSTRAINT degree_of_belonging_pkey PRIMARY KEY (id);
 V   ALTER TABLE ONLY public.degree_of_belonging DROP CONSTRAINT degree_of_belonging_pkey;
       public            postgres    false    226            Z           2606    42632    developer developer_email_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.developer
    ADD CONSTRAINT developer_email_key UNIQUE (email);
 G   ALTER TABLE ONLY public.developer DROP CONSTRAINT developer_email_key;
       public            postgres    false    220            \           2606    42630    developer developer_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.developer
    ADD CONSTRAINT developer_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.developer DROP CONSTRAINT developer_pkey;
       public            postgres    false    220            b           2606    42651    program program_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.program
    ADD CONSTRAINT program_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.program DROP CONSTRAINT program_pkey;
       public            postgres    false    224            f           2606    42681    purchase purchase_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.purchase
    ADD CONSTRAINT purchase_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.purchase DROP CONSTRAINT purchase_pkey;
       public            postgres    false    228            j           2606    42715    review review_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.review
    ADD CONSTRAINT review_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.review DROP CONSTRAINT review_pkey;
       public            postgres    false    232            ^           2606    42639    tag tag_pkey 
   CONSTRAINT     J   ALTER TABLE ONLY public.tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);
 6   ALTER TABLE ONLY public.tag DROP CONSTRAINT tag_pkey;
       public            postgres    false    222            `           2606    42641    tag tag_tag_name_key 
   CONSTRAINT     S   ALTER TABLE ONLY public.tag
    ADD CONSTRAINT tag_tag_name_key UNIQUE (tag_name);
 >   ALTER TABLE ONLY public.tag DROP CONSTRAINT tag_tag_name_key;
       public            postgres    false    222            p           2606    42701 '   daily_stats daily_stats_program_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.daily_stats
    ADD CONSTRAINT daily_stats_program_id_fkey FOREIGN KEY (program_id) REFERENCES public.program(id) ON DELETE CASCADE;
 Q   ALTER TABLE ONLY public.daily_stats DROP CONSTRAINT daily_stats_program_id_fkey;
       public          postgres    false    224    4706    230            l           2606    42665 7   degree_of_belonging degree_of_belonging_program_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.degree_of_belonging
    ADD CONSTRAINT degree_of_belonging_program_id_fkey FOREIGN KEY (program_id) REFERENCES public.program(id) ON DELETE CASCADE;
 a   ALTER TABLE ONLY public.degree_of_belonging DROP CONSTRAINT degree_of_belonging_program_id_fkey;
       public          postgres    false    226    4706    224            m           2606    42670 3   degree_of_belonging degree_of_belonging_tag_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.degree_of_belonging
    ADD CONSTRAINT degree_of_belonging_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES public.tag(id) ON DELETE CASCADE;
 ]   ALTER TABLE ONLY public.degree_of_belonging DROP CONSTRAINT degree_of_belonging_tag_id_fkey;
       public          postgres    false    222    226    4702            k           2606    42652 !   program program_developer_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.program
    ADD CONSTRAINT program_developer_id_fkey FOREIGN KEY (developer_id) REFERENCES public.developer(id);
 K   ALTER TABLE ONLY public.program DROP CONSTRAINT program_developer_id_fkey;
       public          postgres    false    4700    220    224            n           2606    42682 "   purchase purchase_customer_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.purchase
    ADD CONSTRAINT purchase_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customer(id) ON DELETE CASCADE;
 L   ALTER TABLE ONLY public.purchase DROP CONSTRAINT purchase_customer_id_fkey;
       public          postgres    false    218    228    4696            o           2606    42687 !   purchase purchase_program_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.purchase
    ADD CONSTRAINT purchase_program_id_fkey FOREIGN KEY (program_id) REFERENCES public.program(id) ON DELETE CASCADE;
 K   ALTER TABLE ONLY public.purchase DROP CONSTRAINT purchase_program_id_fkey;
       public          postgres    false    224    228    4706            q           2606    42716    review review_customer_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.review
    ADD CONSTRAINT review_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customer(id) ON DELETE CASCADE;
 H   ALTER TABLE ONLY public.review DROP CONSTRAINT review_customer_id_fkey;
       public          postgres    false    232    4696    218            r           2606    42721    review review_program_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.review
    ADD CONSTRAINT review_program_id_fkey FOREIGN KEY (program_id) REFERENCES public.program(id) ON DELETE CASCADE;
 G   ALTER TABLE ONLY public.review DROP CONSTRAINT review_program_id_fkey;
       public          postgres    false    232    224    4706               3   x�3�L�I�H�4200.H-)�/s�L�KI��+*�LL�����b���� ���         D   x�3�LM�0(0�K�L�I�0�35s�L�KI��+*�L.-.��M-�цFƜ1~�FFfz\1z\\\ ��            x������ � �            x�3�4A. i�i����� ��         B   x�3��I-�H4200.H-)�/sH�M���K����O+qI-�/qq�LI-K��/H-�38�b���� ��4         <   x�3�I-.Q((�O/J�U�K�M�4420�30@�II-N.�,(������ZNC�=... ϑ�         .   x�3�4202�50�5�T0��21�26�37�477�4�4����� � �         R   x�3�4�4�4�4202�50�5�T0��21�24�3��42��I-.Q(J-�L-WH�/R((�O/J�U(�,�P�L�5����� v��      	   =   x�3�<����\F��>)|���ӟ���)�~���1�ϟ�~�2I����;�L� M�#�     