PGDMP  +            	    
    {            device-management    16.0    16.0     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16398    device-management    DATABASE     �   CREATE DATABASE "device-management" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
 #   DROP DATABASE "device-management";
                postgres    false            �            1259    16410    device    TABLE     �   CREATE TABLE public.device (
    id uuid NOT NULL,
    address character varying(255),
    description character varying(255),
    hourly_energy_consumption_limit integer,
    owner_id uuid
);
    DROP TABLE public.device;
       public         heap    postgres    false            �          0    16410    device 
   TABLE DATA           e   COPY public.device (id, address, description, hourly_energy_consumption_limit, owner_id) FROM stdin;
    public          postgres    false    215   �       P           2606    16416    device device_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.device
    ADD CONSTRAINT device_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.device DROP CONSTRAINT device_pkey;
       public            postgres    false    215            �   �   x��ϻj�1���S�d˒�:R:u�e)�\��}]�e�*���p���;H�	�S+D�� "e�n�x��w���4n�Sx�1i�Rp�h��4�jo����4ö͑�4��N_�#�Y�9�66���x��fN:��ϜV�he0�B�Tb֭��8z\�,=��e-P*g%R������k�����)���䦐iU����x-DE7�)����!�3X��[��ǧ�_N����{\K�+�s�m�,8h�     