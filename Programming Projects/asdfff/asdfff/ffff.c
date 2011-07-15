void main(void)
{
   unsigned char u8bit; // Unsigned 8-bit number
   signed char s8bit;   // Signed 8-bit number

   unsigned short u16bit;

   u8bit = 166;

   printf("u8bit as an unsigned number = %u\n",u8bit);
   printf("u8bit in hex = %d\n",u8bit);
   printf("u8bit in hex = %x\n",u8bit);

   s8bit = u8bit;
   printf("s8bit as a signed number is = %d\n",s8bit);

   u16bit = 0xABCD; // Hex numbers have the 0x prefix
   printf("u16bit is = %d\n",u16bit);
   return 1;
}