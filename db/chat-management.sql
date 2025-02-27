PGDMP      '                 |            chat-management    16.0    16.0     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    24619    chat-management    DATABASE     �   CREATE DATABASE "chat-management" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
 !   DROP DATABASE "chat-management";
                postgres    false            �            1259    24650    chat_message    TABLE       CREATE TABLE public.chat_message (
    id uuid NOT NULL,
    message character varying(255),
    receiver_uid uuid,
    seen boolean NOT NULL,
    seen_timestamp timestamp(6) without time zone,
    sender_uid uuid,
    sent_timestamp timestamp(6) without time zone
);
     DROP TABLE public.chat_message;
       public         heap    postgres    false            �            1259    24625    users    TABLE     X  CREATE TABLE public.users (
    id uuid NOT NULL,
    name character varying(255),
    password character varying(255),
    user_type character varying(255),
    username character varying(255),
    CONSTRAINT users_user_type_check CHECK (((user_type)::text = ANY ((ARRAY['ADMIN'::character varying, 'CLIENT'::character varying])::text[])))
);
    DROP TABLE public.users;
       public         heap    postgres    false            �          0    24650    chat_message 
   TABLE DATA           s   COPY public.chat_message (id, message, receiver_uid, seen, seen_timestamp, sender_uid, sent_timestamp) FROM stdin;
    public          postgres    false    216   �       �          0    24625    users 
   TABLE DATA           H   COPY public.users (id, name, password, user_type, username) FROM stdin;
    public          postgres    false    215   �       Y           2606    24654    chat_message chat_message_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.chat_message
    ADD CONSTRAINT chat_message_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.chat_message DROP CONSTRAINT chat_message_pkey;
       public            postgres    false    216            U           2606    24634 "   users uk_r43af9ap4edm43mmtq01oddj6 
   CONSTRAINT     a   ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);
 L   ALTER TABLE ONLY public.users DROP CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6;
       public            postgres    false    215            W           2606    24632    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    215            �     x��Z�r\9r]��
� n ���G��[/���<G���-�=��zTQ��t�;b�b�Q��7��s�һ̐ܘ��G6W��+��,����򗗏��Km�;I��u��}r��=V�����B���?z��(�4ˬ�B�m��]��;�8�1c�Xck�o��G<�SՎTG�a�KT�|�8�8��83����xz)�o���G�3{�>�����ÙŎ8�N�4L,oR\��o��/�r��˞S�?�?�Lfqǩ�{Ŧ�!1�%�9����Z�5V8�S	��?O;ތ���� �Î7_3�a3FN1:.�b�0\�5�Y{j�Qx\.m�xf꣄�9���{�������\+'�r�f�I.�����'�r���g^�ŧC��zz����4�kV��X�˩�k"��.����o/�{{��D�s�믦
���΄dqF!B���w���4Y7��[��9V��)9!ֶ<�����8oE���].Y��EM�L�����_���_Q�������I��TuhG=�z(�6�r�*���
ň���O�@^6=�3ʑ�B4yU��]ib��ԃ�V��O��y�R8� �=�/h���S4g}7%�YR�Q���e�̴��U%mD�Ov��Q�'j��Ҏ�Z�,��Y,�Jiy���I��#�4ݣ�G��F��7��B�a�j�?�]1��J��u��ݟG_���3����4\0��Q��E|����I���o����NB�'�������A�j�;������\��WĄ6�׽��w�$���l�W�I8z�\��2Y[����D������۵�nS=A�>�pz�`&o`!39�eJc�</��e��M��1����w�4�3m���Em��hH���Dl��_�������CFO���Y��p���gL���S
�h� �cA�����?z���/A)�_���b��ݨ�o�D!��k @וS�,s�	=���Im�z�5񧷍��l�ԉ���̕l�
�`��>;nN}X����pJc��(xG2o�z�&	�*�+�jY5�d^�Ola�a'J#�T��|0��6��-� ĵ"V��@�/ѕ2�CO�j=ꗿ�׿���������_+�ѱ��%��������  ��+���|��o�\�V�秗���?�w����t#=El3�X-�t��CO�7R���r&^�t����K�c�K�,�������|,��̇��[Q]�G���54]�kI�ɒ/����oZ�Wɑ���\�o%�C9�Z��Wt.�+RJ���� k[��gK
^�/���!��Q:���ԁ�U����֊B� �	a���w�70��a���ރ��0�����I3։�\����(��H�i#��� �	Q���*�*t��ֱ��gb�Ә�����^�o}��a�T���俁�U<��KV 
��
�5ać��l^���i���]�[����A�X$M�n�-<�U�2 �F�&��q'����#fn4�70Q`���zV���KPM�����'@��������-1�
�uvp���t��'K�d�Rh�8ȥo�|$�)�����CF2��0��ek�
��D��bC��y���#���"��D7p$�|Dduk
.]�@��Y ]����s�˯���ig~�4޳�����T��;(+'�պ
�@�v(�8 ����DKox��;I�
FM��a^kN&
'G3h՞ݔԺ�&����Sٓ1z�rR�8�x��a
5:�6�C�4����V3*k+ŷŦ�����d��A%%�.\�q���TS��͍@��������I���N	����c�R���׹�@���Dl�`�{VJ\s���c(�ޟ�}Gȥ��(�y�+����Q�Aw���efK��I���+�>�} �jþ'V�+ʧ� ϵ\ލ��?�m�<��:WF9�|o����
|�9BC�I.ꀈ�Ń=!�y�4y�V�[�|���f��&
���4�+�E��.�.�0� �K^d��D�/#PZ�MM�S������L���2�nB��#��TX�u}�4��]yB�z�m��as���ЙÖ��`2��ÏB�!���a1�6a;��E��\��P><��i�L�I6Gņ9]�ƥR�|��od`��qk{#��<��S�^ẁŃ7�r�	<?�:�c���h�u�j��?;zM�gQ�P���|ժw��DC��ʤ�;e_k)�:���K�W<��0E	ǰ�- ���ץ

.WpC+`CB�����-ܠ����^��X���M-'��/���� �Z��÷�]��p�6ǻ
����X�� .Ai�ʥ��3�7Ɨ9C[tP�PU=��wG{~MK�����=��,Z*:h�5PA�k��J�X���^oF�-��6*�70k4=$��=�u_�	���K����H��T�zڌ�i���~�W�
N��9��ƥu `h٠
;�Cb�����ڶ�38�֤
��$�AUɗ X x6$�T�6f_S�T/O߿6G���Uh��u���S��D�	{۲�Q
r��Tsh�f����X{,1=z[>����{<����+��,Y�֡+^.�[ڻ���%�.�<������K=�L�O���G��阑$����5�����(����f��O�>����>ޛv7||>3��ѧ��/q�\^����!�y*���'��ఎ1���i���R��6I�B�~y�8�����F�Ⱦ����/�=����w�lΏ��TSU.Ți�f�{[}T��u�Rk����?؜��!��L)��[�v�%�AǄ,6J�u�ߵ��J!��{��wx6�Α��gUl���#�xMs�x��Qr����O_�Ʀ�yQh�r���x�t��2k̠S�<<���{2tD��������'lڻ�!	��u:�����B)�Y��C����]s�1ؙ�:�ڌ����Mz����XEA�y��I��ڣ����Ç��c3�lU&��������ƣ6��ن�w���*E����gKkr����,�Zu}ӻ��:Ĕ��Т�_��`��Y����zX�-���up�nVG��~�'GOGhʉ�kYl���u�In�� |��v�I�Ll�N�c��T�,�S]Ll �@�]�2�-�q�������Z��^N/�օ�si^�SƧ����l)��Ǝ5s�QZ����W_���~�v̕uX!Ң�#>�Ւu� 4Z��U���`��`�����6:�=�[oz�zh�SG�PYa.�i���[F$0>���˯��ϧ_��JV�w��=<g�`�3)��1>�5��h�K�Z�h1����~�uڊ��{,�\2����ב3D�j	�;�,�=BS@[�$�W{7��u�uUH�m��<� �h�%󵢅�\g�2duͤp�j���M��n�����@h��C��Ȏ���x$�����Mg#�#��4�ʟ�9BX˭�~���~J�C������Up��Wwh�H���$zF�J��q��^^��%��Ñ��1�؁�h�f٥���Ր���&x���̽? n��b�wk�ox�����q��vQ�      �   P  x�m�;o�0��9�
VS�qn!�*�I(���.�g����\Z�/��ԡÙϫ��&�l"�B"f�
9�YH�B0�Pk���C7v+�*ŵ	��'MƟ�X΃s�`�z�/�m�������κ�4C�u�X��+:{���H�̫��[���,-y^L�.G�� �aI@�v���͖�`�JC�j����poэ�d�#}�{�������t���!vz�t
}0#ZH�<�9{i�6�j�s��v���ٕgu�[AO�0�������	ْ٦����𱗩�W����ؒ�Ǫ�K�&P�M_�0-��(��ߩc9��H$�[���Hަ���I��     