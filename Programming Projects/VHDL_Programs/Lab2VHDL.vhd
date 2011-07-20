
-- This file has been automatically generated by SimUAid.

library ieee;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;

library SimUAid_synthesis;
use SimUAid_synthesis.SimuAid_synthesis_pack.all;

entity Lab2VHDL is
port(A0, B0, C0, D0, E0: in STD_LOGIC;

	M3, M2, M0, M1: out STD_LOGIC
	);
end Lab2VHDL;

architecture Structure of Lab2VHDL is
	signal A_p, Vnet_0, Vnet_1, Vnet_2, Vnet_3, Vnet_4, Vnet_5, Vnet_6, Vnet_7, Vnet_8, 
		Vnet_9, Vnet_10, Vnet_11, B_p, C_p, D_p, E_p: STD_LOGIC;
begin
	VHDL_Device_0: inverter port map (A0, A_p);
	VHDL_Device_1: inverter port map (B0, B_p);
	VHDL_Device_2: inverter port map (C0, C_p);
	VHDL_Device_3: inverter port map (E0, E_p);
	VHDL_Device_4: nand2 port map (A0, E0, Vnet_2);
	VHDL_Device_5: nand2 port map (A_p, E_p, Vnet_1);
	VHDL_Device_6: nand2 port map (A_p, E_p, Vnet_3);
	VHDL_Device_7: nand2 port map (Vnet_1, D0, Vnet_4);
	VHDL_Device_8: nand2 port map (Vnet_1, Vnet_2, M0);
	VHDL_Device_9: nand2 port map (C0, Vnet_4, Vnet_7);
	VHDL_Device_10: nand2 port map (Vnet_3, D_p, Vnet_8);
	VHDL_Device_11: nand2 port map (Vnet_5, B_p, M3);
	VHDL_Device_12: nand2 port map (Vnet_6, Vnet_7, M2);
	VHDL_Device_13: nand2 port map (Vnet_8, Vnet_9, M1);
	VHDL_Device_14: nand4 port map (B_p, C0, Vnet_3, D0, Vnet_5);
	VHDL_Device_15: nand3 port map (Vnet_3, C_p, D0, Vnet_6);
	VHDL_Device_16: nand3 port map (A_p, D0, E_p, Vnet_9);
	VHDL_Device_17: inverter port map (D0, D_p);
end Structure;