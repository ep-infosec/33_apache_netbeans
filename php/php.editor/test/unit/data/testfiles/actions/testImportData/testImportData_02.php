<?php

namespace Foo;

use Baz\Bat;

/**
 * Homepage presenter.
 *
 * @author     John Doe
 * @package    MyApplication
 */
class HomepagePresenter extends BasePresenter { //can not be resolved!!!

    public function renderDefault() {
        $this->template->anyVariable = 'any value';
        Strings::capitalize();
        Arrays::get($arr, $key);
        Html::el();
        new Request();
    }

}

?>