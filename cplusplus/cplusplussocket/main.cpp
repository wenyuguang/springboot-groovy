#pragma execution_character_set("gbk")
#include <QCoreApplication>
#include <client.h>
#include <stdio.h>
#include <iostream>

using namespace std;

int main(int argc, char *argv[])
{
//    int a = 0xbabe;
//    cout << a << endl;
//    QByteArray ba;
//    ba.resize(6);
//    ba[0] = 0x3c;
//    ba[1] = 0xb8;
//    ba[2] = 0x64;
//    ba[3] = 0x18;
//    ba[4] = 0xca;
//    qDebug() <<ba.size();
//    ba.data()[5] = 0x31;
//    qDebug()<<"[]"<<ba[2]; //[] d
//    qDebug()<<"at()"<<ba.at(2); //at() d
//    qDebug()<<"data()"<<ba.data()[2]; //data() d
//    qDebug()<<"constData()"<<ba.constData()[2]; //constData() d
//    qDebug()<<"constData()"<<ba.constData()[5]; //constData() 1

//    int n = 63;
//    qDebug()<<QByteArray::number(n);              // returns "63"
//    qDebug()<<QByteArray::number(a, 16);          // returns "3f"
//    qDebug()<<QByteArray::number(n, 16).toUpper();  // returns "3F"
//    qDebug()<<QByteArray::number(n, 2);          // returns "111111"
//    qDebug()<<QByteArray::number(n, 8);          // returns "77"

    testSimpleTcpSocketClientDemo();
    return 0;
}
