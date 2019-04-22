#pragma execution_character_set("gbk")
#include "client.h"
#include <QTcpSocket>
#include <QDebug>
#include <QTime>
#include <iostream>
#include <stdio.h>
#include<bitset>

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

    char data[] = "hello , im received your msg. and we are connected. �������ģ�����";
    QByteArray array = QByteArray(data, strlen(data));
    m_pTcpSocket->write(array);
    //4. ͬ������-�ȴ����ݿɶ�
//    sendData();
    m_pTcpSocket->waitForReadyRead();
}

void SimpleTcpSocketClientDemo::readyRead()
{
    QByteArray qb = m_pTcpSocket->readAll();

    /*
     *  QByteArray ba("Hello");
        int n = ba.size();          // n == 5
        ba.data()[0];               // returns 'H'
        ba.data()[4];               // returns 'o'
        ba.data()[5];               // returns '\0'
        See also isEmpty() and resize().

        QList<QByteArray> QByteArray::s
    short magic = qb.data()[2];
    char sign = qb.data()[1];
    char status = qb.data()[1];
    char id = qb.data()[8];
    char bodyLength = qb.data()[4];
     */
    short magic = qb.data()[2];
    char sign = qb.data()[1];
    char status = qb.data()[1];
    char id = qb.data()[8];
    char bodyLength = qb.data()[4];

    bitset<32> bs(magic);
    cout << bs << endl;
    cout << sign << endl;
    cout << status << endl;
    cout << id << endl;
    cout << bodyLength << endl;

    cout << qb.size() << endl;
    qDebug() << "received��    " <<qb;

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
