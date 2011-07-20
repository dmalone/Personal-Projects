library ieee;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;

library SimUAid_synthesis;
use SimUAid_synthesis.SimuAid_synthesis_pack.all;

entity Lab3VHDL is
port(y: in std_logic_vector(0 to 3);
    enable: in std_logic; a1, b1, c1: out std_logic);
end Lab3VHDL;

architecture Structure of Lab3VHDL is
begin
a1 <= '0' when enable = '0';
b1 <= '0' when enable = '0';
c1 <= '0' when enable = '0';

a1 <= (enable and (y(3) or y(2)));
b1 <= ((enable) and (y(3) or y(1)) and (y(3) or y(2)));
c1 <= (enable and (y(0) or y(1) or y(2) or y(3)));
end Structure;

