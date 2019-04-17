#include<stdio.h>
#include<string.h>
#include"tables.h"    //数据表

//置换函数
//参数： In：待置换数据指针
//Out：置换输出指针
//	    n:置换表长度
//P：置换表指针
//说明：将输入数据的指定位置作为输出数据的第i位。指定位置即置换表第i位的十进制数。得到的输出数据的长度
//即为置换表的长度。

void myPermutation(char *In, char *Out, int n, char *P)
{
	int i = 0;
	for (i = 0; i < n; i++)
		*(Out + i) = *(In + *(P + i) - 1);
	*(Out + i) = '\0';
}

//按位异或函数
//参数：In1：二进制串1
//In2：二进制串2
//n：二进制串长度
// Out:异或结果
//说明：循环比较两个二进制串每一位，不同则为'1'，相同则为'0'

void myXOR(char* In1, char* In2, int n, char* Out)
{
	int i = 0;
	for (i = 0; i < n; i++)
		*(In1 + i) != *(In2 + i) ? *(Out + i) = '1' : *(Out + i) = '0';
}

//循环左移函数
//参数： In:待移位二进制串
// Out:移位后二进制串
//n:二进制串长度
//s:循环位数
//说明：将输入二进制串移位后对应位置的值赋给输出串，为保证循环（即原二进制串的第一位变成移位后的
//最后一位），将位次相加后与串的长度做模运算。

void myShift(char *In, char *Out, int n, int s)
{
	int i = 0;
	for (i = 0; i < n; i++)
		*(Out + i) = *(In + (s + i) % n);
	*(Out + i) = '\0';
}

//生成子密钥函数
//参数： K:64位的密钥
//(*SK)[49]:得到的一轮子密钥
//说明：输入64位密钥，进行置换选择1，之后进行16轮操作得到16个子密钥，每一轮对56位分成两部分，
//进行相应位数的移位操作，之后再拼接成56位进行置换选择2，得到该轮子密钥

void mySubkey(char* K, char(*SK)[49])
{
	char out[57], C[57], D[29], e[29], t[57];
	int i = 0, j = 0;
	myPermutation(K, out, 56, *PC_1);  //置换选择1
	strcpy_s(C, out);      //C0
	strcpy_s(D, out + 28);   //D0
	for (j = 0; j < 16; j++)
	{
		myShift(C, e, 28, move_time[j]);  //循环左移
		strcpy_s(C, e);                   //Cj
		myShift(D, e, 28, move_time[j]);
		strcpy_s(D, e);                   //Dj
		strncpy_s(t, C, 28);
		strncpy(t + 28, D, 28);
		myPermutation(t, *(SK + j), 48, *PC_2);//置换选择2，得到Kj
	}
}

//轮函数f
//参数：  L: 第t轮的32位L
//R: 第t轮的32位R
//SK: 第t轮的48位子密钥
//t: 轮数
//说明：共进行16轮操作，每轮的32位R先进行拓展置换E变成48位,再与该轮子密钥异或，然后分成
//8组进行S盒代换。每组通过第1，6位组成的二进制串确定S盒行号，通过第2，3，4，5位确定列号，
//找到对应的数并转为4位二进制串。8组代换拼接成32位为S盒代换结果，然后进行置换P。每轮经过
//S盒代换得到的结果与上一轮的L异或作为本轮的R，上一轮的R作为本轮的L。

void myf(char* L, char* R, char* SK, int t)
{
	int i = 0, j = 0;
	int row, col;
	char out1[49] = { 0 }, out2[49] = { 0 }, out3[33] = { 0 }, out4[33] = { 0 }, temp[33] = { 0 };
	printf("K%d=", t + 1);
	puts(SK);
	myPermutation(R, out1, 48, *E);  //扩展置换E
	printf("E(R%d)=", t);
	puts(out1);
	myXOR(out1, SK, 48, out2);      //与子密钥异或
	printf("E(R%d)^K%d=", t, t + 1);
	puts(out2);
	for (i = 0; i < 8; i++)           //S盒代换
	{
		row = ((out2[i * 6] - '0') << 1) + (out2[i * 6 + 5] - '0');   //第1，6位组成行号
		col = ((out2[i * 6 + 1] - '0') << 3) + ((out2[i * 6 + 2] - '0') << 2) + ((out2[i * 6 + 3] - '0') << 1)
			+ (out2[i * 6 + 4] - '0');  //第2，3，4，5位组成列号
		for (j = 3; j >= 0; j--)
			*(out3 + (i * 4 + 3 - j)) = ((S_Box[i][row * 16 + col] >> j) & 1) + '0';  //将取到的S盒数据填到S盒输出的指定位置，先进行十进制转二进制
	}
	*(out3 + 32) = '\0';
	printf("%d轮S盒输出=", t + 1);
	puts(out3);
	myPermutation(out3, out4, 32, *P);  //置换P
	printf("f(R%d,K%d)=", t, t + 1);
	puts(out4);
	strcpy(temp, R);      //保存旧的R
	myXOR(L, out4, 32, R);  //更新R
	printf("R%d=", t + 1);
	puts(R);
	strcpy(L, temp);      //更新L
}


//主函数：
//说明：输入64位明文，先进行初始置换IP操作，接下来将置换输出的 64 位数据分成左右两半，左一半称为 L0 ，
//右一半称为 R0 ，各 32 位。然后进行16轮迭代，迭代过程我和轮函数写在了一起。迭代完成后再经逆IP置换得到密文。

int main()
{
	char* M = "0000000100100011010001010110011110001001101010111100110111101111";
	char* K = "0001001100110100010101110111100110011011101111001101111111110001";
	char out[65], L[33], R[33], SK[16][49], cipher[65];
	int i = 0;
	mySubkey(K, SK);                //产生16轮子密钥
	myPermutation(M, out, 64, *IP);   //初始置换IP
	printf("IP置换：");
	puts(out);
	strcpy(L, out);      //L0
	strcpy(R, out + 32);   //R0
	for (i = 0; i < 16; i++)
	{
		printf("\n-------------------------------第%d轮------------------------------------\n"
			, i + 1);
		myf(L, R, *(SK + i), i);
	}
	strncpy(out, R, 32);   //L16 + R16
	strncpy(out + 32, L, 32);
	myPermutation(out, cipher, 64, *C_IP);    //逆IP置换
	printf("\n密文：");
	puts(cipher);
	return 0;
}
