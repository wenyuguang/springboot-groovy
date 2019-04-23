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
    //1. ����TCP�׽��ֶ���
    m_pTcpSocket = new QTcpSocket(this);
    //2. �����ӡ����ݿɶ���ʧ���ź�����
    connect(m_pTcpSocket, &QTcpSocket::connected, this, &SimpleTcpSocketClientDemo::connected);
    connect(m_pTcpSocket, &QIODevice::readyRead, this, &SimpleTcpSocketClientDemo::readyRead);
    typedef void (QAbstractSocket::*QAbstractSocketErrorSignal)(QAbstractSocket::SocketError);
    connect(m_pTcpSocket, static_cast<QAbstractSocketErrorSignal>(&QTcpSocket::error), this, &SimpleTcpSocketClientDemo::error);
    //3. ��������˽�������
    m_pTcpSocket->connectToHost("127.0.0.1", 8080);

    char data[] = "   hello , im received your msg. and we are connected. �������ģ�����";
    QByteArray array = QByteArray(data, strlen(data));
    m_pTcpSocket->write(array);
    //4. ͬ������-�ȴ����ݿɶ�
    sendData();
    m_pTcpSocket->waitForReadyRead();
}

void SimpleTcpSocketClientDemo::readyRead()
{
    QByteArray qb = m_pTcpSocket->readAll();
//    QByteArray qb1 = m_pTcpSocket->read(2);
    short sst;
    unsigned char B[20];

    memcpy( B, qb.data(), 2);

    qDebug() << B;
//    int s = qb1.data();
//    cout << qb1.data() << endl;
    char s = qb.data()[0];
    short st = qb.toShort();
    qDebug() << "short is :" << QByteArray::number(st, 16);
    qDebug() << "s is ::::" << QByteArray::number(s, 16);

    s = qb.data()[1];
    qDebug() << "s is ::::" << QByteArray::number(s, 16);
    s = qb.data()[2];
    qDebug() << "s is ::::" << QByteArray::number(s, 16);
    s = qb.data()[3];
    qDebug() << "s is ::::" << QByteArray::number(s, 16);
    s = qb.data()[4];
    qDebug() << "s is ::::" << QByteArray::number(s, 16);
    s = qb.data()[5];
    qDebug() << "s is ::::" << QByteArray::number(s, 16);
    s = qb.data()[6];
    qDebug() << "s is ::::" << QByteArray::number(s, 16);
    s = qb.data()[7];
    qDebug() << "s is ::::" << QByteArray::number(s, 16);
    s = qb.data()[8];
    qDebug() << "s is ::::" << QByteArray::number(s, 16);
    s = qb.data()[9];
    qDebug() << "s is ::::" << QByteArray::number(s, 16);
    s = qb.data()[10];
    qDebug() << "s is ::::" << QByteArray::number(s, 16);
    s = qb.data()[11];
    qDebug() << "s is ::::" << QByteArray::number(s, 16);
    s = qb.data()[12];
    qDebug() << "s is ::::" << QByteArray::number(s, 16);
    s = qb.data()[13];
    qDebug() << "s is ::::" << QByteArray::number(s, 16);
    s = qb.data()[14];
    qDebug() << "s is ::::" << QByteArray::number(s, 16);
    s = qb.data()[15];
    qDebug() << "s is ::::" << QByteArray::number(s, 16);


    qDebug() << "size:    " <<qb.size() - 16;

    qDebug() << "received:    " <<qb;

    QString str(qb.data());
    qDebug() << "SimpleTcpSocketClientDemo::readyRead " << str.toStdString().c_str();
    cout << qb.data() << endl;
}
void SimpleTcpSocketClientDemo::sendData(){
    QString str = "asdasdasdasdas hello , im received your msg. and we are connected.";
    QString sWriteData = str.at(qrand() % str.size());
    //��ͻ���д����
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
    //����д���ڴ�й©�����д������ԡ�
    SimpleTcpSocketClientDemo* pSimpleTcpSocketClient = new SimpleTcpSocketClientDemo;
}

#define CLINET_COUNT 1  //�ͻ��˵�����
void testSimpleTcpSocketClientDemo()
{
    QTime oTime;
    oTime.start();

    //ͬ���̳߳صķ�ʽģ�����ͻ�����������˽���
    for (int nIndex = 0; nIndex < CLINET_COUNT; ++nIndex)
    {
        ClientRunnable* pRunnable = new ClientRunnable;
        pRunnable->setAutoDelete(false);
        QThreadPool::globalInstance()->start(pRunnable);
    }

    QThreadPool::globalInstance()->waitForDone(30 * 1000);
    qDebug() << "connect count: " << CLINET_COUNT << "total time: " << (double)oTime.elapsed() / double(1000) << "s";
}
