CREATE SCHEMA "data";

CREATE TABLE "data"."tick" (
  ts     TIMESTAMPTZ NOT NULL,
  ticker TEXT        NOT NULL,
  price  NUMERIC     NOT NULL
);
