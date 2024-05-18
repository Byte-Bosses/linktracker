--liquibase formatted sql

--changeset Nick-552:2
CREATE TABLE IF NOT EXISTS meta_info
(
    link_id         BIGINT                          REFERENCES links (id) NOT NULL,
    key             TEXT                            UNIQUE NOT NULL,
    value           TEXT                            NOT NULL,
    CONSTRAINT link_id_key_unique UNIQUE (link_id, key)
)
--rollback drop table meta_info
