Android DayToDay
================

An Android date math app that allows you to easily:
* Add and subtract days from a date
* Determine number of days or weeks between dates

Overview
--------

This project began as a request from my wife to help the nurses at her practice.
They were counting out dates for shots, follow up appointments, etc on the
calendar. She asked me to make an app that would do it for them. I agreed and
created the app using [ActionBarSherlock] so it would be available across the
widest range of devices.

Usage
-----

This repo contains an importable Eclipse Android project.  Instructions to set
up Eclipse Android development are available [here].  You need to include
[ActionBarSherlock] and [NineOldAndroids] as a library dependencies.  Jake
Wharton has excellent instructions on this process at that link.  You will also
need to include [Joda Time] as a dependency.  Once you have imported the project
and included the library, a Project -> Clean is usually all that is necessary.
If you have any further questions, please let me know.

Developed by
============
* Doyle Young - dyoung@gmail.com

License
=======

    Copyright 2012 Doyle Young

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[Joda Time]: http://joda-time.sourceforge.net/
[NineOldAndroids]: https://github.com/JakeWharton/NineOldAndroids
[ActionBarSherlock]: https://github.com/JakeWharton/ActionBarSherlock
[here]: http://developer.android.com/sdk/index.html
