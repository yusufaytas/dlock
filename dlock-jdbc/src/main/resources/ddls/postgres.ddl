-- This needs to be executed before the ddl
-- CREATE EXTENSION btree_gist;

CREATE TABLE public.lock (
  id          SERIAL PRIMARY KEY,
  name        TEXT,
  owner       TEXT,
  locked_at   TIMESTAMP,
  locked_till TIMESTAMP,
  CHECK ( locked_at < locked_till ),
  CONSTRAINT overlapping_times EXCLUDE USING GIST (
    name WITH =,
    tsrange(locked_at, locked_till) WITH &&
  )
);