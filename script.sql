create table Guest
(
    guestId        varchar(100)             not null
        primary key,
    guestFirstName varchar(100)             not null,
    guestLastName  varchar(100)             not null,
    guestAddress   varchar(100)             not null,
    passportNumber varchar(100)             not null,
    guestCountry   varchar(100)             not null,
    guestContact   varchar(15)              not null,
    guestStatus    varchar(50) default 'in' not null
);

create table RoomType
(
    typeId    int auto_increment
        primary key,
    typeName  varchar(100)  not null,
    typePrice decimal(6, 2) not null
);

create table Room
(
    roomNumber varchar(10) not null
        primary key,
    typeId     int         not null,
    roomStatus varchar(50) not null,
    constraint Room_RoomType_typeId_fk
        foreign key (typeId) references RoomType (typeId)
);

create table User
(
    userId    varchar(50)  not null
        primary key,
    firstName varchar(100) not null,
    email     varchar(100) not null,
    contact   varchar(11)  not null,
    userRole  varchar(50)  not null,
    userName  varchar(100) not null,
    password  varchar(100) not null
);

create table Reservation
(
    resvId       varchar(20)                    not null
        primary key,
    resvDate     date                           not null,
    guestId      varchar(50)                    not null,
    checkInDate  date                           not null,
    checkOutDate date                           not null,
    userId       varchar(50)                    not null,
    status       varchar(20) default 'check-in' null,
    constraint Reservation_FK
        foreign key (guestId) references Guest (guestId),
    constraint Reservation_FK_1
        foreign key (userId) references User (userId)
);

create table CheckOut
(
    checkOutId int auto_increment
        primary key,
    resvId     varchar(20)   not null,
    userId     varchar(50)   not null,
    date       date          not null,
    noOfNight  int           not null,
    totalPrice decimal(7, 2) not null,
    constraint CheckOut_FK
        foreign key (resvId) references Reservation (resvId),
    constraint CheckOut_FK_1
        foreign key (userId) references User (userId)
);

create table ReservationDetail
(
    resvId     varchar(20)   not null,
    roomNumber varchar(10)   not null,
    roomPrice  decimal(6, 2) not null,
    primary key (resvId, roomNumber),
    constraint RoomDetail_FK
        foreign key (resvId) references Reservation (resvId),
    constraint RoomDetail_FK_1
        foreign key (roomNumber) references Room (roomNumber)
);


