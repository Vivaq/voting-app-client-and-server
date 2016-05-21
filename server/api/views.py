from django.shortcuts import render_to_response
from django.template import RequestContext
from api.forms import DocumentForm
from django.views.decorators.csrf import csrf_exempt, csrf_protect
from django.http import HttpResponse


@csrf_exempt
def enter(request):
    if request.method == 'POST':
        print request
        form = DocumentForm(request.POST)
        return HttpResponse('Sent Succesfully', content_type='text/plain')
    else:
        form = DocumentForm()
    return render_to_response(
            'api\index.html',
            {'form': form},
            context_instance=RequestContext(request)
    )
