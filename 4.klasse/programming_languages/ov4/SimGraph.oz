%%% Ett grafisk gränsnitt för simulering av elektriska RC kretsar.
%%%
%%% SKRIVET AV: Dragan Havelka dragan@it.kth.se
%%% SKAPAT:     010825
%%% SENASTE ÄNDRING: $Date: 2002/10/29 09:37:12 $/$Author: dragan $
%%% VERSION:         $Revision: 1.1 $


%% How to check lab2
%%
%% You should have your lab file 'lab3.oz' in the same directory
%% Then start Oz and open rc-visualizer.oz (this file).
%% Feed buffer.
%% If everything works and values in Browse window match
%% you are good ;)
%
functor
import Tk Browser(browse:Browse) System(show:Show)
export make:MakeTest
define
%\insert 'lab3.oz'

   %DT=0.1 % step

% 1|1|1|1|1|...
   fun lazy {Ones}
      1|{Ones}
   end

% 1.0|1.0|1.0|...
   fun lazy {FloatOnes}
      1.0|{FloatOnes}
   end

   proc {MakeTest Lab3Module}

      StreamMap = Lab3Module.streamMap
%      StreamZip = Lab3Module.streamZip
      AddStreams = Lab3Module.streamAdd
      ScaleStream = Lab3Module.streamScale
      Integral = Lab3Module.streamIntegrate
      RC = Lab3Module.makeRC

% 0.0|1.0|2.0|3.0|...
      fun lazy {FloatsByOne}
    0.0|{AddStreams {FloatOnes} {FloatsByOne}}
      end

% 0.0*DT|1.0*DT|
%declare
      fun lazy {FloatsByDt DT}
    0.0|{AddStreams {ScaleStream {FloatOnes} DT} {FloatsByDt DT}}
      end

% 0.0|0.1|0.2|0.3|0.4|...
      fun lazy {Floats}
    {FloatsByDt 0.1}
      end

% sin(0.0)|sin(0.1)|sin(0.2)|....
% argument given i radianer dvs sin(3.14)=0.00159265
      fun lazy {Sins}
    {StreamMap  {Floats} Float.sin}
      end

      fun lazy {Integers}
    1|{AddStreams {Ones} {Integers}}
      end

      class Scheme
    attr
       resistance:5.0
       capacitance:0.5
       dt:0.1
       t:40. % start position i canvasen för att starta fun
       current
       currentCurrent
       voltage
       currentVoltage
       vc0:0.0
       inductance
       rcFun
       window
       currentFrame
       currentStream
       scheme
       diagram
       startButton
       stepButton
       tenStepsButton

   % Init metoden anropas vid instansiering av nytt objekt
    meth init()
       {self createWindow()}
       {self drawScheme()}
       {self drawCurrentTable()}
       {self createDiagram()}
    end

%;*breakok

    meth createWindow()
       F Txt in
       window<-{New Tk.toplevel
           tkInit(title:"Computation with lazy evaluation and streams"
             width:600)}
       currentFrame<-{New Tk.frame
            tkInit(parent:@window height:300 relief:groove bd:2)}
       F={New Tk.frame tkInit(parent:@window relief:groove bd:2)}
       scheme<-{New Tk.canvas
           tkInit(parent:@window bg:gray90
             width:500 height:300)}
       diagram<-{New Tk.canvas
            tkInit(parent:@window bg:white
              width:600 height:300)}
       Txt = {New Tk.label tkInit(parent:@currentFrame text:"Current (i)"
                   /*
                   font:{New Tk.font
                    tkInit(family:lucinda weight:bold)}*/
                  )}
       stepButton<-{New Tk.button tkInit(parent:F text:"step"
                     action:self#start())}
       tenStepsButton<-{New Tk.button tkInit(parent:F text:"ten steps"
                    action:self#start())}
       {Tk.batch [pack(Txt fill:x)
             grid(@currentFrame row:1 column:1 sticky:new padx:3 pady:3)
             grid(F row:2 column:1 sticky:ews padx:3 pady:3)
             pack(@stepButton fill:x)
             pack(@tenStepsButton fill:x)
             grid(@scheme row:1 column:2 rowspan:2 sticky:ew padx:3 pady:3)
             grid(@diagram row:3 column:1 columnspan:2
             sticky:nsew padx:3 pady:3)]}
    end


    meth createDiagram()
       {@diagram tk(crea line arrow:last 30 100 570 100)}
       {@diagram tk(crea line arrow:last 30 200 570 200)}
       {@diagram tk(crea text 560 110 text:"t"
         % font:{New Tk.font tkInit(weight:bold)}
         )}
       {@diagram tk(crea text 560 210 text:"t"
         % font:{New Tk.font tkInit(weight:bold)}
         )}
       {@diagram tk(crea line arrow:last 40 110 40 30)}
       {@diagram tk(crea text 30 30 text:"v"
         % font:{New Tk.font tkInit(weight:bold)}
         )}
       {@diagram tk(crea line arrow:last 40 270 40 120)}
       {@diagram tk(crea text 30 120 text:"i"
         % font:{New Tk.font tkInit(weight:bold)}
         )}
    end


    meth drawScheme()
       {@scheme tk(crea text 35 90 text:"i"
         %font:{New Tk.font tkInit(weight:bold)}
             )}
       {@scheme tk(crea line arrow:last 30 100 100 100)}
       {@scheme tk(crea line 65 100 65 60)}
       {@scheme tk(crea line arrow:last 65 60 100 60)}

       {@scheme tk(crea rectangle 100 50 160 70)}
       {@scheme tk(crea text 130 60 text:"scale:R")}

       {@scheme tk(crea line arrow:last 160 60 310 60)}

       {@scheme tk(crea rectangle 100 90 160 110)}
       {@scheme tk(crea text 130 100 text:"scale:1/C")}

       {@scheme tk(crea line arrow:last 160 100 200 100)}

       {@scheme tk(crea rectangle 200 90 260 110)}
       {@scheme tk(crea text 230 100 text:"integral")}

       {@scheme tk(crea line arrow:last 260 100 310 100)}
       {@scheme tk(crea line 230 145 230 140)}
       {@scheme tk(crea text 230 150 text:"Vo"
         %font:{New Tk.font tkInit(weight:bold)}
             )}
       {@scheme tk(crea line 230 135 230 130)}
       {@scheme tk(crea line arrow:last 230 125 230 110)}

       {@scheme tk(crea line 310 50 310 110)}
       {@scheme tk(crea line 310 50 360 80)}
       {@scheme tk(crea line 310 110 360 80)}
       {@scheme tk(crea text 330 80 text:"add")}

       {@scheme tk(crea text 370 70 text:"v"
         %font:{New Tk.font tkInit(weight:bold)}
             )}
       {@scheme tk(crea line arrow:last 360 80 390 80)}
    end


    meth drawCurrentTable()
       Rs in
       currentStream<-{New Tk.variable tkInit('i(t)=Io')}
       Rs={Map ['i(t)=Io' 'i(t)=Io*t' 'i(t)=sin(t)']
      fun {$ F}
         {New Tk.radiobutton
          tkInit(parent:@currentFrame
            variable:@currentStream
            value:F
            %font:{New Tk.font tkInit(family:lucinda weight:bold)}
            text:F anchor:w)}
      end}
       {Tk.batch [pack(Rs.1 fill:x)
             pack(Rs.2.1 fill:x)
             pack(Rs.2.2.1 fill:x)]}
    end

   % Denna metod används för att stegas igenom strömmar.
   % Den anropar också "drawFunction()" för att rita nya värden för
   % spänning och el.ström
    meth step()
       {self drawFunction()}
       currentVoltage<-@currentVoltage.2
       currentCurrent<-@currentCurrent.2
    end


    meth tenSteps()
       for I in 1..10 do
          {self step()}
       end
    end

   % Denna metod anropar funktionen "RC", binder atributen "rcFun" till
   % returnerad funktion
    meth rc(R C Dt)
       rcFun<-{RC R C Dt}
    end

   % Kollar vilket el.ström kommer att användas under simuleringen
   % och beroende på denna anpassar övriga argument för att diagram ska
   % vara snyggare
    meth checkCurrentSignal()
       C={@currentStream tkReturnAtom($)} in
       if C=='i(t)=Io' then
          current<-{FloatOnes}
       elseif C=='i(t)=Io*t' then
          current<-{Floats}
       else
          current<-{Sins}
       end
    end



   % Anropas när en av knappar "step" eller "tenSteps" trycks på
   % första gången
    meth start()
       {self checkCurrentSignal()}
       {self rc(@resistance @capacitance @dt)}
       voltage<-{@rcFun @current @vc0}
       currentVoltage<-@voltage
       currentCurrent<-@current
       {@stepButton tkAction(action:self#step())}
       {@tenStepsButton tkAction(action:self#tenSteps())}
    end

   % Ritar spänning och el.ström som funktioner i tiden
    meth drawFunction()
       Y1 Y2 C in
      % Här fuskar vi och stegar tiden med 3 punkter för att
      % få snyggare graf.
       C = {@currentStream tkReturnAtom($)}
       if C == 'i(t)=sin(t)' orelse C == 'i(t)=Io' then
          Y2 = 200. -  (@currentCurrent.1*16.0)
          Y1 = 100. -  (@currentVoltage.1*12.0)
       else
          Y2 = 200. -  @currentCurrent.1
          Y1 = 100. -  @currentVoltage.1
       end
       {@diagram tk(crea line @t 100 @t Y1)}
       {@diagram tk(crea line  @t 200 @t Y2)}
       t<-@t+3.
    end
      end
      V1 RC1 RC2 V2 V3
   in
% Skapar en instans av klassen "Scheme"
      {New Scheme init() _}

%declare
      RC1={RC 5.0 1.0 0.2}
      V1={RC1 {FloatOnes} 2.0}
      RC2={RC 5.0 0.5 0.1}
      V2={RC2 {Sins} 2.0}
      V3={Integral {FloatOnes} 5.0 1.0}

      {Show V1.2.2.2.1}
      {Show V2.2.2.2.1}
      {Show V3.2.2.2.1}
      {Show V3}

      {Browse ['Right values for V1 are:' 7.0|7.2|7.4|7.6|_]}
      {Browse ['Your V1 is:' V1]}
      {Browse ['Right values for V2 are:' 2.0|2.49917|3.01331|3.5373|_]}
      {Browse ['Your V2 is:' V2]}
   end
end
