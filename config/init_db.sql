CREATE TABLE resume
(
  uuid      CHAR(36) PRIMARY KEY NOT NULL,
  full_name TEXT                 NOT NULL
);

CREATE TABLE contact
(
  id          SERIAL,
  resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
  type        TEXT     NOT NULL,
  value       TEXT     NOT NULL
);
CREATE UNIQUE INDEX contact_uuid_type_index
  ON contact (resume_uuid, type);


CREATE TABLE public.textsection
(
  id          integer                                    NOT NULL DEFAULT nextval('textsection_id_seq'::regclass),
  type        text COLLATE pg_catalog."default"          NOT NULL,
  value       text COLLATE pg_catalog."default"          NOT NULL,
  resume_uuid character(36) COLLATE pg_catalog."default" NOT NULL,
  CONSTRAINT textsection_pkey PRIMARY KEY (id),
  CONSTRAINT textsection_resume_fk FOREIGN KEY (resume_uuid)
    REFERENCES public.resume (uuid) MATCH SIMPLE
    ON UPDATE RESTRICT
    ON DELETE CASCADE
)
  WITH (
    OIDS = FALSE
  )
  TABLESPACE pg_default;

ALTER TABLE public.textsection
  OWNER to postgres;