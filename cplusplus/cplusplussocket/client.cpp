#pragma execution_character_set("gbk")
#include "client.h"
#include <QTcpSocket>
#include <QDebug>
#include <QTime>
#include <iostream>
#include <stdio.h>
#include <bitset>
#include <string.h>

using namespace std;

SimpleTcpSocketClientDemo::SimpleTcpSocketClientDemo()
{
    //1. 创建TCP套接字对象
    m_pTcpSocket = new QTcpSocket(this);
    //2. 已连接、数据可读、失败信号连接
    connect(m_pTcpSocket, &QTcpSocket::connected, this, &SimpleTcpSocketClientDemo::connected);
    connect(m_pTcpSocket, &QIODevice::readyRead, this, &SimpleTcpSocketClientDemo::readyRead);
    typedef void (QAbstractSocket::*QAbstractSocketErrorSignal)(QAbstractSocket::SocketError);
    connect(m_pTcpSocket, static_cast<QAbstractSocketErrorSignal>(&QTcpSocket::error), this, &SimpleTcpSocketClientDemo::error);
    //3. 与服务器端建立连接
    m_pTcpSocket->connectToHost("127.0.0.1", 8080);

    char data[] = "   hello , im received your msg. and we are connected. 测试中文？？？";
    QByteArray array = QByteArray(data, strlen(data));
    m_pTcpSocket->write(array);
    //4. 同步处理-等待数据可读
    sendData();
    m_pTcpSocket->waitForReadyRead();
}

void SimpleTcpSocketClientDemo::readyRead()
{
    QByteArray qb = m_pTcpSocket->readAll();
//    QByteArray qb = m_pTcpSocket->read(2);
//    qDebug() << qb.toHex();
//    qb = m_pTcpSocket->read(1);
//    qDebug() << qb.toHex();
//    qb = m_pTcpSocket->read(1);
//    qDebug() << qb.toHex();
//    qb = m_pTcpSocket->read(8);
//    qDebug() << qb.toHex();
//    qb = m_pTcpSocket->read(4);
//    qDebug() << qb.toHex();
//    qb = m_pTcpSocket->readAll();
//    qDebug() << qb.data();

    QByteArray hex = qb.mid(0, 2);
    qDebug() << hex.toHex();
    hex = qb.mid(2, 1);
    qDebug() << hex.toHex();
    hex = qb.mid(3, 1);
    qDebug() << hex.toHex();
    hex = qb.mid(4, 8);
    qDebug() << hex.toHex();
    hex = qb.mid(12, 4);
    qDebug() << hex.toHex().data();


    bool ok;
    int dec = hex.toHex().toInt(&ok, 16);
    qDebug() << "dec is :" << dec;
    hex = qb.mid(16, dec);
    qDebug() << "body length:" << hex.size();
    qDebug() << hex.data();
}
void SimpleTcpSocketClientDemo::sendData(){
    QString str = "asdasdasdasdas hello , im received your msg. and we are connected.";
    QString sWriteData = str.at(qrand() % str.size());
    //与客户端写数据
    qDebug() << "SimpleTcpSocketServerDemo::writeDataToClient " << sWriteData;
    m_pTcpSocket->write(sWriteData.toUtf8());
}

void SimpleTcpSocketClientDemo::connected()
{
    qDebug() << "SimpleTcpSocketClientDemo::connected  successfully";
}

void SimpleTcpSocketClientDemo::error(QAbstractSocket::SocketError socketError)
{
    qDebug() << "SimpleTcpSocketClientDemo::error " << socketError;
}

void ClientRunnable::run()
{
    //这样写会内存泄漏，如此写方便测试。
    SimpleTcpSocketClientDemo* pSimpleTcpSocketClient = new SimpleTcpSocketClientDemo;
}

#define CLINET_COUNT 1  //客户端的数量
void testSimpleTcpSocketClientDemo()
{
    QTime oTime;
    oTime.start();

    //同步线程池的方式模拟多个客户端与服务器端交互
    for (int nIndex = 0; nIndex < CLINET_COUNT; ++nIndex)
    {
        ClientRunnable* pRunnable = new ClientRunnable;
        pRunnable->setAutoDelete(false);
        QThreadPool::globalInstance()->start(pRunnable);
    }

    QThreadPool::globalInstance()->waitForDone(30 * 1000);
    qDebug() << "connect count: " << CLINET_COUNT << "total time: " << (double)oTime.elapsed() / double(1000) << "s";
}
