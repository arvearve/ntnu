% Task 2:  Map a function over a stream
declare
fun lazy {StreamMap S F}
   case S
   of nil then nil
   [] H | T then
      {F H} | {StreamMap T F}
   end
end

% Task 3: Zip streams
declare
fun lazy {StreamZip S1 S2 F}
   case S1#S2
   of (S1H|S1T)#(S2H|S2T) then
      {F S1H S2H} | {StreamZip S1T S2T F}
   else
      nil
   end
end

% Task 4: Scaling streams
declare
fun lazy {StreamScale SF Factor}
   {StreamMap SF fun {$ X} X*Factor end}
end

% Task 5: Adding Streams
declare
fun lazy {StreamAdd SF1 SF2}
   {StreamZip SF1 SF2 fun {$ A B} A+B end}
end

% Task 6: Integrating streams
fun lazy {StreamIntegrate SF InitValue Dt}
      case SF
      of SFh|SFt then
    local Curr in
       Curr = InitValue + SFh * Dt
       InitValue|{StreamIntegrate SFt Curr Dt}
    end
      else
         nil 
      end
end

% Task 7: Simulating RC Circuits
% v = v_0 + 1/C * integral[i(t)] dt from 0 to t + R_i
declare
fun lazy {MakeRC R C Dt}
   fun lazy {$ SF V0}
      {StreamAdd
       {StreamIntegrate {StreamScale SF 1.0/C} V0 Dt}
       {StreamScale SF R}}
   end
end




% Tests
% Expected outcomes:
% 5|10|15|20|_
% 4|10|18|_
% 2.0|4.0|6.0|8.0|_
% 3.0|4.0|5.0|_
% 5.0|6.0|6.0|_
% 2.0|17.0|35.0|_
% 7.0|7.2|7.4|7.6|7.8|_


% Test for StreamMap
local 
   fun {MultiplyByFive N} N*5 end
in
   Fives = {StreamMap 1|2|3|4|_ MultiplyByFive}
   {Nth Fives 4 _}
   {Browse Fives}
end

% Test for StreamZip
local
   fun {Multiply X Y} X*Y end
in
   A = {StreamZip 1|2|3|_ 4|5|6|_ Multiply}
   {Nth A 3 _}
   {Browse A}
end

% Test for StreamScale
local A in
   A = {StreamScale  1.0|2.0|3.0|4.0|_ 2.0}
   {Nth A 4 _}
   {Browse A}
end

% Test for StreamAdd
local S in
   S = {StreamAdd 1.0|1.0|1.0|_ 2.0|3.0|4.0|_}
   {Nth S 4 _}
   {Browse S}
end

% Test StreamIntegrate
local A B in
   A = {StreamIntegrate 1.0|0.0|1.0|_ 5.0 1.0}
   B = {StreamIntegrate 5.0|6.0|7.0|_ 2.0 3.0}
   {Nth A 3 _}
   {Nth B 3 _}
   {Browse A}
   {Browse B}
end

% Test MakeRC
local 
   RC = {MakeRC 5.0 1.0 0.2}
   Vs = {RC 1.0|1.0|1.0|1.0|1.0|_ 2.0}
in
   {Browse Vs}
   {Nth Vs 5 _}
end


Streams = streams(streamMap: StreamMap
        streamZip: StreamZip
        streamScale: StreamScale
        streamIntegrate: StreamIntegrate
        streamAdd: StreamAdd
        makeRC: MakeRC)

local
   % No idea how to set working directory. Had to use absolute path.
   Test={Module.link ['SimGraph.ozf']}.1.make

in
   {Test Streams}
end

