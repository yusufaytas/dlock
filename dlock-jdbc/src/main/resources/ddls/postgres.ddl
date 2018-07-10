-- =========================================================================
-- Copyright © 2018 Yusuf Aytas. All rights reserved.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
-- =========================================================================

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