PGDMP              	    
    {            user-management    16.0    16.0     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16399    user-management    DATABASE     �   CREATE DATABASE "user-management" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
 !   DROP DATABASE "user-management";
                postgres    false            �            1259    16400    users    TABLE     X  CREATE TABLE public.users (
    id uuid NOT NULL,
    name character varying(255),
    password character varying(255),
    user_type character varying(255),
    username character varying(255),
    CONSTRAINT users_user_type_check CHECK (((user_type)::text = ANY ((ARRAY['ADMIN'::character varying, 'CLIENT'::character varying])::text[])))
);
    DROP TABLE public.users;
       public         heap    postgres    false            �          0    16400    users 
   TABLE DATA           H   COPY public.users (id, name, password, user_type, username) FROM stdin;
    public          postgres    false    215   �       Q           2606    16409 "   users uk_r43af9ap4edm43mmtq01oddj6 
   CONSTRAINT     a   ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);
 L   ALTER TABLE ONLY public.users DROP CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6;
       public            postgres    false    215            S           2606    16407    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    215            �   �  x�UVI��J\ۿ�.���
�v�8�</�F�8��"�eW��#O����Ӓ(H[��)c�D��;��V%�����o`�������c���v���EO�ט�h�h��
\�_=�E��q[���f8���+��x4�1�"�X!��
jh&]�y��(�8��W��#aז�"���ۗR�lt�Qw�Omsq��&�|1˚�΁���v����a%y�s��?�x�|аl�r� �B�b�l�@�(U�'��J�XL�jwx��(�����m�r?�*�'ˤ��'��/Bt�MK�k�����VR�p(�%-e6�������y 1L�S�S�II52�7_5���e���q����z+�w��N�'c�ؽc�{���c��KC|O����8�� ĳ�20t����*�IQ���zJ_$��)ۙi&m�=�ߎ�I�<��������_iv�ך��X$��ޖ���J���+π6к�6,�QiG�?Ry��
�3���k�*Y�4J�'Y6�}��l�p����t\���t��G~о�0Tz#*05�+���'�ץ��?`�y�n�Sc�������]�
�8\�yw�-�N�0�J�}kk������i*��U<��!ZH m-B$DB�\ya9���;2��`�����0íR��OO*������<�I�hM��R�B@̀��E�4(�L���,���p;��{�+�߻�)����zy<��n�{+���>�W��:��6�X�ũms(��:��3�M�aGQ���6Q��o����ƥ�X�K�ç���1�׈�����"󺰃l���~	.��2�+j8�\+ã��ۘH�c��FtKb�v��b�6�K�<t�r�j��&ZN�!Uio�|@����Z��i8G��E	2�ـ���TҒ��,G�%���mJ�y��o��oMvR\i��!�
���<�2�yo�a"��E7�e�^������{�>~��10v,��Xpj]����=�	�s*�c,h����3I�k���p����{mf%N�<�Ůݢ�ZԚ����$Sy��^,څ�"¾g~0��c鵱A�gI#N*up���]�b��t;�b3y�ﺮ�vE;��t�B��O7sk2����������;�Ω=ߏ����I�g~�1�İ1�6��ꆢܰ��ۂ�R�<�4}?��
��[���ٷ�c�I��k�J{�;M�ܸ�|�^�hUY����y(<n�R[OpKS��0(L'ۄ�XkSx?�l�.iRs��w�Ej�"��3:�k�irY��iP߇��<k9>�|	�4"a���� �J_j�ٜ��.%�ԟ?z��1X�Gv��uޜTk]Sf�ku�p5k݆�/x��V<X/&�ϿG}�.#X �@Զ�\� RZ�>Z�0���/���#4wRh�CыF����8JA�E��Q������v�4͝u潿q��A����.a:�:A��HCBEs��A�/%�:�����d�Cd;Ir.������p`F����i�`�9&!�K�G뱼߹���� ������=KP��l��CH�o�|�pn��8�]�S��Fɱ{�̠Q�{�n6�Њ]���!�~>�7��-?U��_%\K�I��aa�������u�_��ŕ�a+���V�Ë��4�����r�z��do����Џۦf:�q8F�x?D���5�o;|�������/*��     