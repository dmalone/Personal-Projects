library ieee;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;


entity SM is
          Port (rst, CLK, X: in std_logic;
                   Q1, Q2, Q3, Z: out std_logic);
end SM;

architecture Structure of SM is
  signal state, nextstate: integer range 0 to 7;
     signal state_output: std_logic_vector(2 downto 0);
  begin
  state_output <= conv_std_logic_vector(state,3);
  Q3 <= state_output(2);
  Q2 <= state_output(1);
  Q1 <= state_output(0);

  process(state,X)
  begin
    case state is
    when 0 =>
      if X = '0' then Z <= '0'; nextstate <= 1;
      else Z <= '1'; nextstate <= 2; end if;
    when 1 =>
      if X = '0' then Z <= '1'; nextstate <= 3;
      else Z <= '0'; nextstate <= 3; end if;
    when 2 =>
      if X = '0' then Z <= '0'; nextstate <= 3;
      else Z <= '1'; nextstate <= 4; end if;
    when 3 =>
      if X = '0' then Z <= '0'; nextstate <= 5;
      else Z <= '1'; nextstate <= 6; end if;
    when 4 =>
      if X = '0' then Z <= '1'; nextstate <= 6;
      else Z <= '0'; nextstate <= 6; end if;
    when 5 =>
      if X = '0' then Z <= '1'; nextstate <= 0;
      else Z <= '0'; nextstate <= 0; end if;
    when 6 =>
      if X = '0' then Z <= '0'; nextstate <= 0;
      else Z <= 'X'; nextstate <= 0; end if;
    when 7 =>
      if X = '0' then Z <= 'X'; nextstate <= 0;
      else Z <= 'X'; nextstate <= 0; end if;
    end case;
  end process;

  process (CLK, rst)
  begin
    if (rst = '0') then
        state <= 0;
    elsif (CLK'event and CLK = '1') then 
       state  <= nextstate;
    end if;
  end process;
end Structure;
