--liquibase formatted sql

--changeset Nick-552:2
CREATE TABLE IF NOT EXISTS meta_info
(
    link_id         BIGINT                          REFERENCES links (id) NOT NULL,
    key             TEXT                            NOT NULL,
    value           TEXT                            ,
    PRIMARY KEY (link_id, key)
)
--rollback drop table meta_info
