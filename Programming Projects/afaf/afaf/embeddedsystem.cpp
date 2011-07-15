void main(void)
{
   unsigned char sw1, sw2, lt; //declare two switches and 1 light
   
   sw1 = 0x01;
   sw2 = 0x01;
   // light ON only of both sw1 and sw2 are ON
   if (sw1 & sw2) {  // Condition check
       lt = 0x01;
       printf("Light On");
   }
   else {
       lt = 0x00;
       printf("Light Off");
   }
}