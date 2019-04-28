#pragma execution_character_set("gbk")
#include "client.h"
#include <QTcpSocket>
#include <QDebug>
#include <QTime>
#include <iostream>
#include <stdio.h>
#include <bitset>
#include <string.h>
#include <qfile.h>
#include <qdatastream.h>
#include <qprocess.h>
#include <qsettings.h>
#include <qdir.h>
#include <QCoreApplication>

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
    m_pTcpSocket->connectToHost("127.0.0.1", 8081);

    char data[] = "   hello , im received your msg. and we are connected. �������ģ�����";
    QByteArray array = QByteArray(data, strlen(data));
    m_pTcpSocket->write(array);
    //4. ͬ������-�ȴ����ݿɶ�
    sendData();
    m_pTcpSocket->waitForReadyRead();
}

void SimpleTcpSocketClientDemo::readyRead(){

    QByteArray qb = m_pTcpSocket->read(2);
    qDebug() << qb.toHex();
    qb = m_pTcpSocket->read(1);
    qDebug() << qb.toHex();
    qb = m_pTcpSocket->read(1);
    qDebug() << qb.toHex();
    qb = m_pTcpSocket->read(8);
    qDebug() << qb.toHex();
    qb = m_pTcpSocket->read(4);
    bool ok;
    int dec = qb.toHex().toInt(&ok, 16);
    qDebug() << "dec is :" << dec;
    while(m_pTcpSocket->bytesAvailable() < dec){
        if(!m_pTcpSocket->waitForReadyRead()){
            qDebug() << m_pTcpSocket->errorString();
        }
    }
    qb = m_pTcpSocket->readAll();

    qDebug() << "total byte size is :" << qb.size();

    QFile file("F:/1.jar");
    file.open(QIODevice::WriteOnly);
    QDataStream out(&file);
    out.setVersion(QDataStream::Qt_5_12);
    out << qb;
    file.close();
    qDebug() << "body hex length:" << qb.size();

    QProcess p(0);
//    p.start("cmd", QStringList()<<"/c"<<"cd f:");
    p.start("cmd", QStringList()<<"/c"<<"javaw -jar f://1.jar");
    p.waitForStarted();
    p.waitForFinished();
    QString strTemp=QString::fromLocal8Bit(p.readAllStandardOutput());

    qDebug() << strTemp;

}
void SimpleTcpSocketClientDemo::sendData(){
    QString str = "asdasdasdasdas hello , im received your msg. and we are connected.";
    QString sWriteData = str.at(qrand() % str.size());
    //��ͻ���д����
    qDebug() << "SimpleTcpSocketServerDemo::writeDataToClient " << sWriteData;
    m_pTcpSocket->write(sWriteData.toUtf8());
}

void SimpleTcpSocketClientDemo::connected(){
    qDebug() << "SimpleTcpSocketClientDemo::connected  successfully";
}

void SimpleTcpSocketClientDemo::error(QAbstractSocket::SocketError socketError){
    qDebug() << "SimpleTcpSocketClientDemo::error " << socketError;
}

void ClientRunnable::run(){
    //����д���ڴ�й©�����д������ԡ�
    SimpleTcpSocketClientDemo* pSimpleTcpSocketClient = new SimpleTcpSocketClientDemo;
}

#define CLINET_COUNT 1  //�ͻ��˵�����
void testSimpleTcpSocketClientDemo(){
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

/**
 * ϵͳ������
 * @brief appAutoRun
 * @param bAutoRun
 */
void appAutoRun(bool bAutoStart){
#ifdef Q_OS_WIN32
    QSettings reg("HKEY_CURRENT_USER\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run", QSettings::NativeFormat);
    QString strAppPath = QDir::toNativeSeparators(QCoreApplication::applicationFilePath());
    QString strAppName = QFileInfo(strAppPath).baseName();

    reg.setValue(strAppName, bAutoStart ? strAppPath: "");
#endif

#ifdef Q_OS_LINUX
    //д.desktop�ļ�, ��/etc/xdg/autostartĿ¼��
#endif

#ifdef Q_OS_MACOS
    if (bAutoStart){
        LSSharedFileListRef loginItems = LSSharedFileListCreate(NULL, kLSSharedFileListSessionLoginItems, NULL);
        CFURLRef url = (CFURLRef)[NSURL fileURLWithPath:QStringToNSString(appPath)];
        LSSharedFileListItemRef item = LSSharedFileListInsertItemURL(loginItems, kLSSharedFileListItemLast, NULL, NULL, url, NULL, NULL);
        CFRelease(item);
        CFRelease(loginItems);
    }else{
        UInt32 seedValue;
        CFURLRef thePath;
        LSSharedFileListRef loginItems = LSSharedFileListCreate(NULL, kLSSharedFileListSessionLoginItems, NULL);
        CFArrayRef loginItemsArray = LSSharedFileListCopySnapshot(loginItems, &seedValue);
        for (id item in (NSArray *)loginItemsArray) {
            LSSharedFileListItemRef itemRef = (LSSharedFileListItemRef)item;
            if (LSSharedFileListItemResolve(itemRef, 0, (CFURLRef*) &thePath, NULL) == noErr){
                if ([[(NSURL *)thePath path] hasPrefix:QStringToNSString(appPath)]){
                    LSSharedFileListItemRemove(loginItems, itemRef);
                }
                CFRelease(thePath);
            }
        }
        CFRelease(loginItemsArray);
        CFRelease(loginItems);
    }
#endif
}
