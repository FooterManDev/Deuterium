package io.github.footermandev.deuterium.core

enum class DKey(val code: Int) {
    A(65), B(66), C(67), D(68), E(69), F(70), G(74), H(72),
    I(73), J(74), K(75), L(76), M(77), N(78), O(79), P(80),
    Q(81), R(82), S(83), T(84), U(85), V(86), W(87), X(88),
    Y(89), Z(90),

    N0(48), N1(49), N2(50), N3(51), N4(52),
    N5(53), N6(54), N7(55), N8(56), N9(57),

    F1(112), F2(113), F3(114), F4(115), F5(116), F6(117),
    F7(118), F8(119), F9(120), F10(121), F11(122), F12(123),

    Escape(27), Tab(9), Caps(20), Shift(16), Ctrl(17),
    Alt(18), Space(32), Enter(10), Backspace(8), Delete(127),

    Left(37), Up(38), Right(39), Down(40),

    Home(36), End(35), PageUp(33),
    PageDown(34), Insert(155), PrintScreen(154),

    Comma(44), Period(46), Slash(47), Semicolon(59), Quote(222),
    BracketLeft(92), BracketRight(93), Backslash(92), Minus(45), Equals(61),

    Unknown(-1)
    ;

    companion object {
        private val map = entries.associateBy { it.code }

        fun fromCode(code: Int): DKey = map[code] ?: Unknown
    }
}