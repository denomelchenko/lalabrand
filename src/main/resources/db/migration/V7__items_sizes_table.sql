create table if not exists items_sizes(
    item_info_id varchar(36),
    size_id varchar(36),
    is_size_available boolean,
    primary key (item_info_id, size_id),
    foreign key (item_info_id) references item_info(id),
    foreign key (size_id) references size(id)
);

alter table item_info
    drop foreign key item_info_ibfk_2,
    drop column size_id;