
function generatePass(len) {
    var keylistalpha = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    var keylistint = '123456789';
    var keylistspec = '!?@#$%ß+-_';
    var temp = '';

    var lenInt = 2;
    var lenSpecial = 1;
    var lenAlpha = len - lenInt - lenSpecial;

    for (i = 0; i < lenAlpha; i++)
        temp += keylistalpha.charAt(Math.floor(Math.random() * keylistalpha.length));

    for (i = 0; i < lenSpecial; i++)
        temp += keylistspec.charAt(Math.floor(Math.random() * keylistspec.length));

    for (i = 0; i < lenInt; i++)
        temp += keylistint.charAt(Math.floor(Math.random() * keylistint.length));

    return temp;
}
