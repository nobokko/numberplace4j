customElements.define('np-cell', class extends HTMLElement {
    static get observedAttributes() {
        return [
            'data-row',
            'data-column',
            'value',
        ];
    }

    constructor() {
        super();

        const template_style = `
            <style>
                #textbox {
                    width: 100%;
                    height: 100%;

                    display: flex; /* 親要素をFlexboxにする */
                    justify-content: center; /* 横を中央揃えにする */
                    align-items: center; /* 縦を中央揃えにする */
                }
            </style>
        `.replace(/\n/g, '').replace(/ {2,}/g, ' ');
        const template_body = `
            <div id="textbox" contenteditable></div>
        `.replace(/\n/g, '').replace(/ {2,}/g, ' ');

        const shadow_root = this.attachShadow({ mode: 'open' });
        shadow_root.innerHTML = template_style + template_body;

        let component = this;
        shadow_root.querySelector('#textbox')?.addEventListener('input', ev => {
            console.log(ev);
            let old_value = component.getAttribute('value');
            let new_value = ev.currentTarget?.innerText.replace(/[！-～]/g, function(s){
                return String.fromCharCode(s.charCodeAt(0) - 0xFEE0);
            }).replace(/\n/g, '');
            if (new_value != null && new_value != '') {
                new_value = Number.parseInt(new_value);
                if (isNaN(new_value)) {
                    new_value = old_value;
                } else {
                    if (new_value < 1) {
                        new_value = old_value;
                    } else if (9 < new_value) {
                        new_value = old_value;
                    }
                }
            }
            component.setAttribute('value', new_value);
        });
    }

    getInputHiddenTag(force=false) {
        console.debug(this);
        if (!this.hiddentag || force) {
            if (!this.hiddentag) {
                this.hiddentag = document.createElement('input');
            }
            this.hiddentag.type = 'hidden';
            this.hiddentag.name = 'cell['+ this.dataset.row +']['+ this.dataset.column +']';
            this.hiddentag.value = this.attributes.value.value;
        }
        console.debug(this.hiddentag);

        return this.hiddentag;
    }

    attributeChangedCallback(name, oldValue, newValue) {
        switch (name) {
            case 'data-row':
                this.getInputHiddenTag(true);
                break;
            case 'data-column':
                this.getInputHiddenTag(true);
                break;
            case 'value':
                console.debug([oldValue,newValue]);
                if (this.shadowRoot.querySelector('#textbox').innerText != newValue) {
                    this.shadowRoot.querySelector('#textbox').innerText = newValue;
                }
                this.getInputHiddenTag().value = newValue;
                break;
        }
    }
});