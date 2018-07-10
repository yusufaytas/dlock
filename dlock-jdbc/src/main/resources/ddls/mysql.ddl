CREATE TABLE public.lock (
  id          MEDIUMINT NOT NULL AUTO_INCREMENT,
  name        TEXT,
  owner       TEXT,
  locked_at   TIMESTAMP,
  locked_till TIMESTAMP,
  PRIMARY KEY (id),
  CHECK ( locked_at < locked_till )
);